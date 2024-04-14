package service.impl;

import dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import repositoty.UserInfoRepository;
import repositoty.entity.UserInfoEntity;
import service.IUserInfoService;
import util.CmmUtil;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;
    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertUserInfo Start");

        String userId = CmmUtil.nvl(pDTO.userId());
        String pwd = CmmUtil.nvl(pDTO.userPassword());
        String email = CmmUtil.nvl(pDTO.userEmail());
        String name = CmmUtil.nvl(pDTO.userName());
        String nick = CmmUtil.nvl(pDTO.userNickname());

        log.info("pDTO : " + pDTO);


        /**
         * return = 0 : 실패
         * return = 1 : 성공
         * return = 2 : 아이디 중복
         * return = 3 : 이메일 중복
         * return = 4 : 별명 중복
         */
        if (userInfoRepository.findByUserId(userId).isPresent()) {
           log.info(this.getClass().getName() + ".return : 2 (아이디 중복)");
            log.info(this.getClass().getName() + ".insertUserInfo End");
            return 2; // 아이디 중복
        }
        if (userInfoRepository.findByUserEmail(email).isPresent()) {
            log.info(this.getClass().getName() + ".return : 3 (이메일 중복)");
            log.info(this.getClass().getName() + ".insertUserInfo End");
            return 3; // 이메일 중복
        }
        if (userInfoRepository.findByUserNickname(nick).isPresent()) {
            log.info(this.getClass().getName() + ".return : 4 (별명 중복)");
            log.info(this.getClass().getName() + ".insertUserInfo End");
            return 4; // 별명 중복
        }

        UserInfoEntity pEntity = UserInfoEntity.builder()
                .userId(userId)
                .pwd(pwd)
                .email(email)
                .userName(name)
                .nickname(nick)
                .build();

        UserInfoEntity savedEntity = userInfoRepository.save(pEntity);

        // 성공적으로 저장되었다면, DB에서 다시 조회하여 확인
        if (userInfoRepository.findByUserId(savedEntity.getUserId()).isPresent()) {
            log.info(this.getClass().getName() + ".return : 1 (회원가입 성공)");
            log.info(this.getClass().getName() + ".insertUserInfo End");
            return 1; // 회원가입 성공
        } else {
            log.info(this.getClass().getName() + ".return : 0 (회원가입 실패 : 에러)");
            log.info(this.getClass().getName() + ".insertUserInfo End");
            return 0; // DB 저장 후 조회 실패
        }
    }
}
