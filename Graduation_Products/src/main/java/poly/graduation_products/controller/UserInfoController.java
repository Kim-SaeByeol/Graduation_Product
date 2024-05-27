package poly.graduation_products.controller;

import jakarta.servlet.http.HttpSession;
import poly.graduation_products.dto.MsgDTO;
import poly.graduation_products.dto.UserInfoDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.graduation_products.service.IMailService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.util.EncryptUtil;
import poly.graduation_products.service.IUserInfoService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value =  "/user")
public class UserInfoController {

    private final IUserInfoService userInfoService;
    private final IMailService mailService;

    /**
     * 로그인
     */
    @GetMapping(value = "/login")
    public String login() {
        log.info(this.getClass().getName() + "로그인 페이지");
        return "user/login";
    }

    /**
     * 회원가입
     */
    @GetMapping(value = "/userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() +"회원가입 페이지");
        return "/user/userRegForm";
    }

    /**
     * 아이디 찾기
     */
    @GetMapping(value = "/searchUserId")
    public String searchUserId() {
        log.info(this.getClass().getName() +"아이디 찾기 페이지");
        return "user/searchUserId";
    }

    /**
     * 비밀번호 찾기
     */
    @GetMapping(value = "/searchPassword")
    public String searchPassword() {
        log.info(this.getClass().getName() +"비밀번호 찾기 페이지");
        return "user/searchPassword";
    }

    /**
     * 비밀번호 재설정
     */
    @GetMapping(value = "/newPassword")
    public String newPassword() {
        log.info(this.getClass().getName() +"비밀번호 재설정 페이지");
        return "user/newPassword";
    }

    /**
     * #####################################################################################
     *                                  Post 영역
     * #####################################################################################
     */

    /**
     * 아이디 중복 체크
     */
    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public String getUserIdExists(HttpServletRequest request) throws Exception {

        log.info("controller 아이디 중복체크 실행");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        String existsYn = userInfoService.UserIdExists(userId);

        log.info("existsYn : " + existsYn);
        log.info("controller 아이디 중복체크 완료");

        return "{\"exists\": \"" + existsYn + "\"}";

    }


    /**
     * 이메일 중복 체크
     */
    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public String getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "이메일 중복체크 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        String existsYn = userInfoService.UserEmailExists(email);

        log.info("existsYn : " + existsYn);

        log.info(this.getClass().getName() + "이메일 중복체크 실행");

        return "{\"exists\": \"" + existsYn + "\"}";
    }

    /**
     * 별명 중복 체크
     */
    @ResponseBody
    @PostMapping(value = "getNickExists")
    public String getNickExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "별명 중복체크 실행");

        String nick = CmmUtil.nvl(request.getParameter("nick"));

        log.info("nick : " + nick);

        String existsYn = userInfoService.UserNickExists(nick);

        log.info("existsYn : " + existsYn);

        log.info(this.getClass().getName() + "별명 중복체크 실행");

        return "{\"exists\": \"" + existsYn + "\"}";
    }

    /**
     * 회원가입
     */
    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUser(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName()  + "회원가입 실행");

        String msg = "";

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String nickname = CmmUtil.nvl(request.getParameter("nick"));
        String userName = CmmUtil.nvl(request.getParameter("name"));

        log.info("userId : " + userId);
        log.info("password : " + password);
        log.info("email : " + email);
        log.info("nickname : " + nickname);
        log.info("userName : " + userName);


        /**
         * res 는 결과를 저장할 변수
         * 0 : 실패
         * 1 : 성공
         * 2 : 이미 사용자가 존재 (실패)
         * 3 : 에러
         */
        int res = userInfoService.insertUserInfo(userId, password, email, nickname, userName);

        log.info("res : " + res);

        if (res == 1) {
            msg = "회원가입에 성공하였습니다.";

        } else if (res == 2) {
            msg = "이미 존재하는 아이디 입니다.";

        } else {
            msg = "회원가입에 실패하였습니다.";

        }

        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();
        log.info("리턴 값 : " + rmsg);
        log.info(this.getClass().getName()  + "회원가입 종료");

        return rmsg;
    }


    /**
     * 로그인
     */
    @ResponseBody
    @PostMapping(value = "loginProc", produces = "application/json; charset=UTF-8")
    public int getLogin(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller 로그인 실행");


        /**
         * res 의 값에 따라 결과가 다름
         * 0 => 로그인 실패
         * 1 => 로그인 실행
         */
        int res = 0;

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));

        log.info("userId : " + userId);
        log.info("password : " + password);

        try {
            res = userInfoService.Login(userId, password);

            if (res == 1) {
                session.setAttribute("SS_USER", userId);
            }

        } catch (Exception e) {
            log.warn(e.toString());
            e.printStackTrace();

        }

        log.info("res : " + res);

        log.info("controller 로그인 종료");

        return res;
    }

    /**
     * 아이디 찾기
     */
    @ResponseBody
    @PostMapping(value = "searchUserId")
    public String searchUserId(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "아이디 찾기 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));
        String userName = CmmUtil.nvl(request.getParameter("name"));

        log.info("email : " + email);
        log.info("userName : " + userName);

        String userId = userInfoService.searchUserId(userName, email);

        log.info("userId : " + userId);

        log.info(this.getClass().getName() + "아이디 찾기 종료");

        return "{\"userId\": \"" + userId + "\"}";
    }

    /**
     * 비밀번호 찾기
     */
    @ResponseBody
    @PostMapping(value = "searchPassword")
    public int searchPassword(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "비밀번호 찾기 실행");

        String msg = "";
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("userId : " + userId);
        log.info("userName : " + userName);
        log.info("email : " + email);

        int res = userInfoService.searchPassword(userId, userName, email);

        log.info("res : " + res);

        log.info(this.getClass().getName() + "비밀번호 찾기 종료");

        return res;
    }

    /**
     * 회원가입 이메일 인증번호
     */
    @ResponseBody
    @PostMapping(value = "UserEmailAuthNumber")
    public UserInfoDTO UserEmailAuthNumber(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "이메일 중복체크 실행");


        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("email : " + email);

        //이메일을 통해 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.UserEmailAuthNumber(email))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + "이메일 중복체크 종료");

        return rDTO;
    }

    /**
     * 아이디 찾기 이메일 인증번호
     */
    @ResponseBody
    @PostMapping(value = "getEmailAuthNumber")
    public UserInfoDTO getEmailAuthNumber(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "이메일 중복체크 실행");


        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("email : " + email);

        //이메일을 통해 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.emailAuthNumber(email))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + "이메일 중복체크 종료");

        return rDTO;
    }

    /**
     * 비밀번호 재설정
     */
    @ResponseBody
    @PostMapping("newPassword")
    public int newPassword(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "비밀번호 재설정 실행");


        String userId = request.getParameter("userId");
        String newpassword = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));

        log.info("userID : " + userId);
        log.info("newpassword : " + newpassword);

        int res = userInfoService.newPassword(userId, newpassword);

        log.info("res : " + res);

        log.info(this.getClass().getName() + "비밀번호 재설정 종료");
        return res;
    }

}