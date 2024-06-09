package poly.graduation_products.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import poly.graduation_products.repository.UserInfoRepository;
import poly.graduation_products.repository.entity.UserInfoEntity;
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
    private final UserInfoRepository userInfoRepository;

    /**
     * 로그인
     */
    @GetMapping(value = "/login")
    public String login() {
        log.info(this.getClass().getName() + "일반 로그인 페이지");
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
    public String newPassword(HttpSession session,  RedirectAttributes redirectAttributes) {
        log.info(this.getClass().getName() +"비밀번호 재설정 페이지");

        String Check = (String) session.getAttribute("Check");
        session.removeAttribute("Check");


        if (Check == null) {
            log.info("세션 값이 존재하지 않음 Check : " + Check);
            return "redirect:/user/searchPassword";
        }

        log.info("세션 값이 존재함 Check : " + Check);
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
    public MsgDTO getUserIdExists(HttpServletRequest request) throws Exception {

        log.info("controller 아이디 중복체크 실행");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        int res = 0;
        String msg = "";

        res = userInfoService.UserIdExists(userId);

        if (res == 1) {
            msg = "사용 가능한 아이디 입니다.";
        } else {
            msg = "사용 중인 아이디가 있습니다.";
        }


        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();

        log.info("controller 아이디 중복체크 완료");

        return rmsg;

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
    public MsgDTO getNickExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "별명 중복체크 실행");

        int res = 0;
        String msg = "";

        String nick = CmmUtil.nvl(request.getParameter("nick"));

        log.info("nick : " + nick);

        res = userInfoService.UserNickExists(nick);

        log.info("res : " + res);

       if(res == 1) {
           msg = "가입 가능한 별명입니다.";
        } else {
           msg = "이미 가입된 별명이 존재합니다.";
       }

        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();

        log.info(this.getClass().getName() + "별명 중복체크 종료");

        return rmsg;
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
    public MsgDTO getLogin(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller 로그인 실행");


        /**
         * res 의 값에 따라 결과가 다름
         * 0 => 로그인 실패
         * 1 => 로그인 실행
         */
        int res = 0;
        String msg = "";

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));

        log.info("userId : " + userId);
        log.info("password : " + password);

        try {
            res = userInfoService.Login(userId, password);

            if (res == 1) {
                // 세션에 유저의 별명 혹은 이름을 같이 저장.
                Optional<UserInfoEntity> optionalUserInfo = userInfoRepository.findByUserId(userId);
                String nickname = optionalUserInfo.map(UserInfoEntity::getNickname) // nickname 가져오기
                                .orElse(userId); // 만약 닉넹미이 없다면 아이디를 사용함.

                session.setAttribute("SS_USER_NAME", nickname); // 세션에 이름 저장
                msg = nickname + "님 어서오세요.";

            } else {
                msg = "로그인에 실패하셨습니다.";
            }

        } catch (Exception e) {
            log.warn(e.toString());
            e.printStackTrace();

        }

        log.info("res : " + res);
        log.info("msg : " + msg);

        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();

        log.info("controller 로그인 종료");

        return rmsg;
    }



    /**
     * 아이디 찾기
     */
    @ResponseBody
    @PostMapping(value = "searchUserId")
    public String searchUserId(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "아이디 찾기 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));
        String nickName = CmmUtil.nvl(request.getParameter("nickName"));

        log.info("email : " + email);
        log.info("nickName : " + nickName);

        String userId = userInfoService.searchUserId(nickName, email);

        log.info("userId : " + userId);

        log.info(this.getClass().getName() + "아이디 찾기 종료");

        return "{\"userId\": \"" + userId + "\"}";
    }

    /**
     * 비밀번호 찾기
     */
    @ResponseBody
    @PostMapping(value = "searchPassword")
    public MsgDTO searchPassword(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + "비밀번호 찾기 실행");

        String msg = "";
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String nickName = CmmUtil.nvl(request.getParameter("nickName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("userId : " + userId);
        log.info("nickName : " + nickName);
        log.info("email : " + email);

        int res = userInfoService.searchPassword(userId, nickName, email);

        if (res == 1){
            msg = "사용자를 찾았습니다. 비밀번호를 재설정하세요.";
            //세션 저장
            session.setAttribute("userId", userId);
            session.setAttribute("Check", "Ok");
        } else {
            msg = "비밀번호를 찾을 수 없습니다. 입력 정보를 다시 확인해주세요.";
        }

        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();


        log.info(this.getClass().getName() + "비밀번호 찾기 종료");

        return rmsg;
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
    public MsgDTO newPassword(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + "비밀번호 재설정 실행");

        int res = 0;
        String msg = "";
        MsgDTO rmsg;

        // 세션에서 값 불러오기
        String userId = (String) session.getAttribute("userId");

        // 세션 값 삭제
        session.removeAttribute("userId");

        // 유저가 입력한 비밀번호 받아오기
        String newpassword = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));

        log.info("userID : " + userId);
        log.info("newpassword : " + newpassword);

        if (userId == null){
            log.info("userId : " + userId);

            res = 2;
            msg = "세션이 만료되었습니다. 비밀번호 찾기를 다시 진행해주세요.";


            rmsg = MsgDTO.builder()
                    .res(res)
                    .msg(msg)
                    .build();

            return rmsg;
        }

        res = userInfoService.newPassword(userId, newpassword);

        if(res == 1) {
            msg = "비밀번호가 성공적으로 재설정되었습니다.";

             rmsg = MsgDTO.builder()
                    .res(res)
                    .msg(msg)
                    .build();
        } else {
            msg = "해당 사용자가 존재하지 않습니다. 다시 확인해주세요.";

             rmsg = MsgDTO.builder()
                    .res(res)
                    .msg(msg)
                    .build();
        }


        log.info(this.getClass().getName() + "비밀번호 재설정 종료");

        return rmsg;
    }

    /**
     * 로그아웃
     */
    @PostMapping("logout")
    @ResponseBody
    public int logout(HttpSession session) {
        try {
            // 특정 세션 속성만 제거
            session.removeAttribute("SS_USER_NAME");
            return 1;  // 로그아웃 성공 응답
        } catch (Exception e) {
            return 0;  // 로그아웃 실패 응답
        }
    }

    @GetMapping("testMyPage")
    public String testMyPage() {
        log.info(this.getClass().getName() + "로그인 페이지");
        return "user/testMyPage";
    }}