package poly.graduation_products.service.impl;

import poly.graduation_products.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.repositoty.UserInfoRepository;
import poly.graduation_products.service.IUserInfoService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.repositoty.entity.UserInfoEntity;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;


    /**
     * 회원가입 하기
     * @param pDTO DB에 저장할 값
     * @return DB에 저장할 rDTO
     */
    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".inserUserInfo Start! (회원가입)");

        int res = 0;

        String userId = CmmUtil.nvl(pDTO.userId());
        String password = CmmUtil.nvl(pDTO.userPassword());
        String userName = CmmUtil.nvl(pDTO.userName());
        String nick = CmmUtil.nvl(pDTO.userNickname());
        String email = CmmUtil.nvl(pDTO.userEmail());


        log.info("pDTO : " + pDTO);


        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            res = 2;
        } else {
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId)
                    .pwd(password)
                    .userName(userName)
                    .nickname(nick)
                    .email(email)
                    .build();

            userInfoRepository.save(pEntity);

            rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) {
                res = 1;
            } else {
                res = 0;
            }
        }

        log.info(this.getClass().getName() + ".inserUserInfo End! (회원가입)");

        return res;
    }


    // 아이디 중복체크
    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserIdExists Start! (아이디 중복체크)");

        UserInfoDTO rDTO;

        String userId = CmmUtil.nvl(pDTO.userId());
        log.info("userId : " + userId);
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        //
        if(rEntity.isPresent()) {
            rDTO = UserInfoDTO.builder().existsIdYn("Y").build();
        } else {
            rDTO = UserInfoDTO.builder().existsIdYn("N").build();
        }

        log.info("retrun 값 : " + rDTO.existsIdYn());
        log.info(this.getClass().getName() + ".getUserIdExists End! (아이디 중복체크)");


        return rDTO;
    }

    // 이메일 중복체크
    @Override
    public UserInfoDTO getUserEmailExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserEmailExists Start! (이메일 중복체크)");

        UserInfoDTO rDTO;

        String email = CmmUtil.nvl(pDTO.userEmail());
        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

        if(rEntity.isPresent()) {
            rDTO = UserInfoDTO.builder().existsEmailYn("Y").build();
        } else {
            rDTO = UserInfoDTO.builder().existsEmailYn("N").build();
        }
        log.info("retrun 값 : " + rDTO.existsEmailYn());
        log.info(this.getClass().getName() + ".getUserEmailExists End! (이메일 중복체크)");

        return rDTO;
    }


    //별명 중복체크
    @Override
    public UserInfoDTO getUserNickExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserNickExists Start! (별명 중복체크)");

        UserInfoDTO rDTO;

        String nick = CmmUtil.nvl(pDTO.userNickname());
        log.info("nick : " + nick);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByNickname(nick);

        if(rEntity.isPresent()) {
            rDTO = UserInfoDTO.builder().existsNickYn("Y").build();
        } else {
            rDTO = UserInfoDTO.builder().existsNickYn("N").build();
        }
        log.info("retrun 값 : " + rDTO.existsNickYn());
        log.info(this.getClass().getName() + ".getUserNickExists End! (별명 중복체크)");

        return rDTO;
    }
}
