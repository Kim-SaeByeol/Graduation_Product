package poly.graduation_products.service.impl;

import org.springframework.dao.DataAccessException;
import poly.graduation_products.dto.MailDTO;
import poly.graduation_products.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.repositoty.UserInfoRepository;
import poly.graduation_products.service.IUserInfoService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.repositoty.entity.UserInfoEntity;
import poly.graduation_products.util.DateUtil;
import poly.graduation_products.util.RandomUtil;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final MailService mailService;


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

    @Override
    public int getUserLogin(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getUserLogin Start! (로그인)");

        int res = 0;

        String userId = CmmUtil.nvl(pDTO.userId());
        String password = CmmUtil.nvl(pDTO.userPassword());

        log.info("userId : " + userId);
        log.info("password : " + password);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndPwd(userId, password);

        if (rEntity.isPresent()) {
            log.info("아이디와 비밀번호가 일치합니다.");
                res = 1;
        } else {
            log.info("아이디와 비밀번호가 일치하지 않습니다.");
        }
        log.info(this.getClass().getName() + ".getuserLoginCheck End! (로그인)");

        return res;
    }


    // 아이디 찾기
    @Override
    public String searchUserIdProc(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".searchUserIdProc Start!");

        String res = "";

        String userName = CmmUtil.nvl(pDTO.userName());
        String email = CmmUtil.nvl(pDTO.userEmail());

        log.info("pDTO userName : " + userName);
        log.info("pDTO email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserNameAndEmail(pDTO.userName(), pDTO.userEmail());

        log.info("rEntity : " + rEntity);

        if (rEntity.isPresent()) {
            UserInfoEntity userInfoEntity = rEntity.get();
            String userId = userInfoEntity.getUserId();
            log.info("Found userId: " + userId); // userId가 성공적으로 조회되었을 때 로그 출력
            res = userId;
        }

        log.info(this.getClass().getName() + ".searchUserIdProc End!");

        return res;
    }

    @Override
    public UserInfoDTO getEmailAuthNumber(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getEmailAuthNumber Start! (인증번호 발송)");

        UserInfoDTO rDTO;
        String OTP = "";
        String email = CmmUtil.nvl(pDTO.userEmail());
        String existsEmailYn = "";


        try {
            log.info("email : " + email);
            Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

            if (rEntity.isPresent()) {
                existsEmailYn = "Y";
                log.info("existsEmailYn : Y (등록 이메일 있음)");

                OTP = RandomUtil.generateRandomCode(6);  // 인증번호
                log.info("OTP : " + OTP);

                rDTO = UserInfoDTO.builder()
                        .authNumber(OTP)
                        .existsEmailYn(existsEmailYn)
                        .build();

                String toMail = email; // 받는 사람
                String title = "[케어트랙] 이메일 인증번호 발송";    // 제목
                String text = "케어트랙 이메일 인증번호 : [" + OTP+"]\n 해당 인증번호를 입력해주세요."; // 내용

                MailDTO dto = MailDTO.builder()
                        .title(title)
                        .text(text)
                        .toMail(toMail)
                        .build();
                mailService.sendMail(dto); // 이메일 발송
            } else {
                existsEmailYn = "N";
                log.info("existsEmailYn : N(등록 이메일 없음)");
            }
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 예외 발생, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
        } catch (Exception e) {
            log.error("기타 예외 처리, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
        }
        rDTO = UserInfoDTO.builder()
                .authNumber(OTP)
                .existsEmailYn(existsEmailYn)
                .build();
        log.info("retrun 값 : " + rDTO.existsEmailYn());
        log.info(this.getClass().getName() + ".getEmailAuthNumber End! (인증번호 발송)");

        return rDTO;
    }

    // 비밀번호 찾기(재생성)
    @Override
    public String searchPasswordProc(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".searchPasswordProc Start! (비밀번호 재생성)");

        String res = "";

        String userId = CmmUtil.nvl(pDTO.userId());
        String userName = CmmUtil.nvl(pDTO.userName());
        String email = CmmUtil.nvl(pDTO.userEmail());

        log.info("pDTO userId : " + userId);
        log.info("pDTO userName : " + userName);
        log.info("pDTO email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndUserNameAndEmail(pDTO.userId(), pDTO.userName(), pDTO.userEmail());

        log.info("rEntity : " + rEntity);

        if (rEntity.isPresent()) {
            UserInfoEntity userInfoEntity = rEntity.get();
            String password = userInfoEntity.getPwd();
            log.info("Found password: " + password); // userId가 성공적으로 조회되었을 때 로그 출력
            res = password;
        }

        log.info(this.getClass().getName() + ".searchPasswordProc end! (비밀번호 재생성)");

        return res;
    }


    // 비밀번호 재설정
    @Override
    public String newPasswordProc(UserInfoDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".newPasswordProc Start (비밀번호 재설정)");
        String res = "";

        String userId = CmmUtil.nvl(pDTO.userId());
        String pwd = CmmUtil.nvl(pDTO.userPassword());
        log.info("userId : " + userId);

        Optional<UserInfoEntity> uEntity = userInfoRepository.findByUserId(userId);

        if(uEntity.isPresent()) {
            UserInfoEntity rEntity = uEntity.get();
            log.info("rEntity : " + rEntity);

            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userSeq(rEntity.getUserSeq())
                    .userId(rEntity.getUserId())
                    .pwd(pwd)
                    .email(rEntity.getEmail())
                    .userName(rEntity.getUserName())
                    .nickname(rEntity.getNickname())
                    .userSince(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .roles("회원")
                    .build();

            log.info("pEntity : " + pEntity);

            userInfoRepository.save(pEntity);

            res = "success";

            log.info("비밀번호 업데이트!!");
        } else {
            log.info("비밀번호 업데이트 실행 안됨");
            res = "false";
        }


        log.info(this.getClass().getName() + ".newPasswordProc End (비밀번호 재설정)");
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
        String OTP = "";
        String email = CmmUtil.nvl(pDTO.userEmail());
        String existsEmailYn = "";


        try {
            log.info("email : " + email);
            Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

            if (rEntity.isPresent()) {
                existsEmailYn = "Y";
                log.info("existsEmailYn : Y");
            } else {
                existsEmailYn = "N";
                log.info("existsEmailYn : N(중복 없음)");


                OTP = RandomUtil.generateRandomCode(6);  // 인증번호
                log.info("OTP : " + OTP);

                rDTO = UserInfoDTO.builder()
                        .authNumber(OTP)
                        .existsEmailYn(existsEmailYn)
                        .build();

                String toMail = email; // 받는 사람
                String title = "[케어트랙] 이메일 인증번호 발송";    // 제목
                String text = "케어트랙 이메일 인증번호 : [" + OTP+"]\n 해당 인증번호를 입력해주세요."; // 내용

                MailDTO dto = MailDTO.builder()
                        .title(title)
                        .text(text)
                        .toMail(toMail)
                        .build();
                mailService.sendMail(dto); // 이메일 발송

            }
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 예외 발생, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
        } catch (Exception e) {
            log.error("기타 예외 처리, 에러내용 : ", e);
            rDTO = UserInfoDTO.builder().existsEmailYn("E").build();
        }
        rDTO = UserInfoDTO.builder()
                .authNumber(OTP)
                .existsEmailYn(existsEmailYn)
                .build();
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
