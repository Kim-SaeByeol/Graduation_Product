package poly.graduation_products.service.impl;

import org.springframework.transaction.annotation.Transactional;
import poly.graduation_products.dto.MailDTO;
import poly.graduation_products.dto.UserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.repositoty.UserInfoRepository;
import poly.graduation_products.service.IUserInfoService;
import poly.graduation_products.repositoty.entity.UserInfoEntity;
import poly.graduation_products.util.RandomUtil;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserInfoService implements IUserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final MailService mailService;


    @Override
    public String UserIdExists(String userId) throws Exception {
        log.info(this.getClass().getName() + "아이디 중복체크 시작");

        String existsYn;

        log.info("userId : " + userId);

        // 아이디 중복체크
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            existsYn = "N";     // 중복이 아님

        } else {
            existsYn = "Y";     // 중복 임

        }
        log.info(this.getClass().getName() + "아이디 중복체크 종료");


        return existsYn;
    }

    @Override
    public String UserEmailExists(String email) throws Exception {
        log.info(this.getClass().getName() + "이메일 중복체크 시작");

        String existsYn;

        log.info("email : " + email);

        // 이메일 중복체크
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(email);

        if (rEntity.isPresent()) {
            existsYn = "N";     // 중복이 아님

        } else {
            existsYn = "Y";     // 중복 임

        }

        log.info(this.getClass().getName() + "이메일 중복체크 종료");

        return existsYn;
    }

    @Override
    public String UserNickExists(String nickName) throws Exception {
        log.info(this.getClass().getName() + "별명 중복체크 시작");

        String existsYn;

        log.info("nickName : " + nickName);

        // 닉네임 중복체크
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByNickname(nickName);

        if (rEntity.isPresent()) {
            existsYn = "N";     // 중복이 아님

        } else {
            existsYn = "Y";     // 중복 임

        }

        log.info(this.getClass().getName() + "별명 중복체크 종료");


        return existsYn;
    }

    @Override
    public int insertUserInfo(String userId, String password, String email, String nickname, String userName) throws Exception {
        log.info(this.getClass().getName() + "회원가입 시작");

        /**
         * res 의 값에 따라 결과가 다름
         * 1 => 회원가입 진행
         * 2 => 아이디가 이미 있음
         */
        int res = 0;

        // 아이디 중복확인
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        if (rEntity.isPresent()) {
            res = 2;

        } else {

            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .userName(userName)
                    .build();

            userInfoRepository.save(pEntity);

            rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) {
                res = 1;

            }

        }

        log.info(this.getClass().getName() + "회원가입 종료");

        return res;
    }

    @Override
    public int Login(String userId, String password) throws Exception {
        log.info(this.getClass().getName() + "로그인 실행");
        /**
         * res 의 값에 따라 결과가 다름
         * 0 => 로그인 실패
         * 1 => 로그인 실행
         */
        int res = 0;

        log.info("userId : " + userId);
        log.info("password : " + password);

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;

        }

        log.info(this.getClass().getName() + "로그인 종료");

        return res;
    }

    @Override
    public String searchUserId(String userName, String email) throws Exception {

        log.info(this.getClass().getName() + "아이디 찾기 실행");

        log.info("userName : " + userName);
        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserNameAndEmail(userName, email);

        String userId = null;

        // 찾기가 잘 실행되었다면 아이디를 DB에서 가져옴
        if (rEntity.isPresent()) {
            userId = rEntity.get().getUserId();
        }

        log.info(this.getClass().getName() + "아이디 찾기 종료");

        return userId;
    }

    @Override   // 이메일이 없을 경우 인증번호 발생 후 회원가입
    public UserInfoDTO UserEmailAuthNumber(String email) throws Exception {
        log.info(this.getClass().getName() + "이메일 인증번호 발송 실행");

        UserInfoDTO rDTO;
        String OTP = "";
        String existsYn = "";

        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

        if (rEntity.isPresent()) {
            existsYn = "Y";
            log.info("existsEmailYn : Y (등록 이메일 있음)");

        } else {
            existsYn = "N";
            log.info("existsEmailYn : N(등록 이메일 없음)");

            OTP = RandomUtil.generateRandomCode(6);  // 인증번호
            log.info("OTP : " + OTP);

            rDTO = UserInfoDTO.builder()
                    .authNumber(OTP)
                    .existsEmailYn(existsYn)
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

        rDTO = UserInfoDTO.builder()
                .authNumber(OTP)
                .existsEmailYn(existsYn)
                .build();
        log.info("retrun 값 : " + rDTO.existsEmailYn());

        log.info(this.getClass().getName() + "이메일 인증번호 발송 종료");

        return rDTO;    }

    @Override  //이메일이 있을 경우 인증번호 발생
    public UserInfoDTO emailAuthNumber(String email) throws Exception {

        log.info(this.getClass().getName() + "이메일 인증번호 발송 실행");

        UserInfoDTO rDTO;
        String OTP = "";
        String existsYn = "";

        log.info("email : " + email);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByEmail(email);

        if (rEntity.isPresent()) {
            existsYn = "Y";
            log.info("existsEmailYn : Y (등록 이메일 있음)");

            OTP = RandomUtil.generateRandomCode(6);  // 인증번호
            log.info("OTP : " + OTP);

            rDTO = UserInfoDTO.builder()
                    .authNumber(OTP)
                    .existsEmailYn(existsYn)
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
            existsYn = "N";
            log.info("existsEmailYn : N(등록 이메일 없음)");
        }

        rDTO = UserInfoDTO.builder()
                .authNumber(OTP)
                .existsEmailYn(existsYn)
                .build();
        log.info("retrun 값 : " + rDTO.existsEmailYn());

        log.info(this.getClass().getName() + "이메일 인증번호 발송 종료");

        return rDTO;
    }


    @Override
    public int searchPassword(String userId, String userName, String email) throws Exception {

        log.info(this.getClass().getName() + "비밀번호 찾기 실행");
        /**
         * res 의 값에 따라 결과가 다름
         * 0 => 비밀번호 찾기 실패
         * 1 => 비밀번호 찾기 성공
         */
        int res = 0;

        Optional<UserInfoEntity> rEntity = userInfoRepository
                .findByUserIdAndUserNameAndEmail(userId, userName, email);

        if (rEntity.isPresent()) {
            res = 1;

        }

        log.info(this.getClass().getName() + "비밀번호 찾기 종료");

        return res;
    }

//    @Override
//    public String newPassword(String userId, String newPassword) throws Exception {
//        log.info(this.getClass().getName() + "비밀번호 재설정 실행");
//
//        String res = "";
//
//        log.info("userId : " + userId);
//
//        Optional<UserInfoEntity> uEntity = userInfoRepository.findByUserId(userId);
//
//        if(uEntity.isPresent()) {
//            UserInfoEntity rEntity = uEntity.get();
//            log.info("rEntity : " + rEntity);
//
//            UserInfoEntity pEntity = UserInfoEntity.builder()
//                    .userSeq(rEntity.getUserSeq())
//                    .userId(rEntity.getUserId())
//                    .password(newPassword)
//                    .email(rEntity.getEmail())
//                    .userName(rEntity.getUserName())
//                    .nickname(rEntity.getNickname())
//                    .build();
//
//            log.info("pEntity : " + pEntity);
//
//            userInfoRepository.save(pEntity);
//
//            res = "success";
//
//            log.info("비밀번호 업데이트!!");
//        } else {
//            log.info("비밀번호 업데이트 실행 안됨");
//            res = "false";
//        }
//        log.info(this.getClass().getName() + "비밀번호 재설정 종료");
//        return res;
//    }

    @Override
    @Transactional
    public int newPassword(String userId, String newPassword) {
        log.info(this.getClass().getName() + ": 비밀번호 재설정 실행");


        /**
         * res 결과에 따라 결과가 달라짐
         * 0 : 실패
         * 1 : 성공
         */
        int res = 0;

        log.info("userId: " + userId);
        log.info("newPassword: " + newPassword);

        Optional<UserInfoEntity> uEntity = userInfoRepository.findByUserId(userId);

        if (uEntity.isPresent()) {
            UserInfoEntity rEntity = uEntity.get();
            log.info("rEntity: " + rEntity);

            // 여기에서 비밀번호 해싱을 추가하세요 (예: passwordEncoder.encode(newPassword))
            rEntity.setPassword(newPassword); // 비밀번호만 업데이트

            userInfoRepository.save(rEntity);

            res = 1;
            log.info("비밀번호 업데이트 완료!");
        } else {
            log.info("해당 사용자가 존재하지 않습니다.");
        }
        log.info(this.getClass().getName() + ": 비밀번호 재설정 종료");
        return res;
    }
    @Override
    public void updateUserInfo(String userId, String nickname) throws Exception {

    }

    @Override
    public void deleteUserInfo(String userId) throws Exception {

    }
}
