package poly.graduation_products.service.impl;

import org.springframework.dao.DataAccessException;
import poly.graduation_products.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.repositoty.UserInfoRepository;
import poly.graduation_products.service.IUserInfoService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.repositoty.entity.UserInfoEntity;
import poly.graduation_products.util.DateUtil;

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

        /**
         * res 는 결과를 저장할 변수
         * 0 : 실패
         * 1 : 성공
         * 2 : 이미 사용자가 존재 (실패)
         * 3 : 에러
         */
        int res = 0;

        String userId = CmmUtil.nvl(pDTO.userId());
        String pwd = CmmUtil.nvl(pDTO.userPassword());
        String email = CmmUtil.nvl(pDTO.userEmail());
        String name = CmmUtil.nvl(pDTO.userName());
        String nick = CmmUtil.nvl(pDTO.userNickname());
        String since = DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss");
        String roles = "회원";
        log.info("pDTO : " + pDTO);


        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            log.info("ID가 [ " + userId + " ]인 회원이 이미 존재합니다. : " + userId);
            res = 2;    //사용자가 이미 존재할 경우
            return res;
        } else {
            //사용자가 존재하지 않을 경우
            //회원가입 로직 진행
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId)
                    .pwd(pwd)
                    .email(email)
                    .userName(name)
                    .nickname(nick)
                    .userSince(since)
                    .roles(roles)
                    .build();

            log.info("userId (아이디) : " + userId);
            log.info("pwd    (비밀번호) : " + pwd);
            log.info("email  (이메일) : " + email);
            log.info("name   (이름) :  " + name);
            log.info("nick   (별명) : " + nick);
            log.info("since  (가입일자) : " + since);
            log.info("roles  (권한) : " + roles);

            try {
                UserInfoEntity savedEntity = userInfoRepository.save(pEntity);
                if (savedEntity != null) {
                    log.info("DB 저장 완료 : " + savedEntity);
                    res = 1; // 성공적으로 저장되었음을 나타냄
                } else {
                    log.info("DB 저장 실패");
                    res = 0; // 저장 실패
                }
            } catch (Exception e) {
                res = 3;    // 오류 발생
                log.error("회원가입 중 예외 발생", e);
                throw new Exception("DB 처리 중 오류가 발생했습니다.", e); // 예외를 다시 던져서 상위 레이어로 전파
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

        try {
            String userId = CmmUtil.nvl(pDTO.userId());
            log.info("userId : " + userId);
            Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) {
                rDTO = UserInfoDTO.builder().existsIdYn("Y").build();
            } else {
                rDTO = UserInfoDTO.builder().existsIdYn("N").build();
            }
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 예외 발생, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsIdYn("E").build();
        } catch (Exception e) {
            log.error("기타 예외 처리, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsIdYn("E").build();
        }
        log.info("return 값 : " + rDTO.existsIdYn());
        log.info(this.getClass().getName() + ".getUserIdExists End! (아이디 중복체크)");
        return rDTO;
    }

    // 이메일 중복체크
    @Override
    public UserInfoDTO getUserEmailExists(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserEmailExists Start! (이메일 중복체크)");

        UserInfoDTO rDTO;

        try {
            String email = CmmUtil.nvl(pDTO.userEmail());
            log.info("email : " + email);
            Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

            if (rEntity.isPresent()) {
                rDTO = UserInfoDTO.builder().existsEmailYn("Y").build();
            } else {
                rDTO = UserInfoDTO.builder().existsEmailYn("N").build();
            }
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 예외 발생, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
        } catch (Exception e) {
            log.error("기타 예외 처리, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
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
