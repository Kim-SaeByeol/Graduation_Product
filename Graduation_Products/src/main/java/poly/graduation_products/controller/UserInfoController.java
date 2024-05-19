package poly.graduation_products.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.ModelMap;
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

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value =  "/user")
public class UserInfoController {

    private final IUserInfoService userInfoService;
    private final IMailService mailService;

    //회원가입 폼
    @GetMapping(value = "userRegForm")
    public String userRegForm() {

        log.info(this.getClass().getName() + ".userRegForm Start!(회원가입 화면)");
        log.info(this.getClass().getName() + ".userRegForm End!(회원가입 화면)");

        return "user/userRegForm";
    }

    //아이디찾기 폼
    @GetMapping(value = "searchUserId")
    public String searchUserId() {

        log.info(this.getClass().getName() + ".searchUserId Start!(아이디 찾기 화면)");
        log.info(this.getClass().getName() + ".searchUserId End!(아이디 찾기 화면)");

        return "user/searchUserId";
    }

    //회원가입하기
    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName()  + ".insertUserInfo Start! (회원가입)");

        String msg = null;
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        //비밀번호는 개인정보인 만큼 암호화를 진행
        String pwd = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("pwd")));
        // 이메일은 개인정보인 만큼 암호화를 진행
        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));
        String name = CmmUtil.nvl(request.getParameter("name"));
        String nick = CmmUtil.nvl(request.getParameter("nick"));


        log.info("userId : " + userId );
        log.info("password : " + pwd );
        log.info("email : " + email );
        log.info("userName : " + name );
        log.info("userNickname : " + nick );


        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .userPassword(pwd)
                .userEmail(email)
                .userName(name)
                .userNickname(nick)
                .build();

        /**
         * res 는 결과를 저장할 변수
         * 0 : 실패
         * 1 : 성공
         * 2 : 이미 사용자가 존재 (실패)
         * 3 : 에러
         */
        int res = userInfoService.insertUserInfo(pDTO);

        log.info("회원가입 결과(res) : " + res );
        if (res == 0){
            msg = "회원가입이 실패하였습니다. \n 고객센터 연락 바랍니다.";
        } else if(res == 1) {
            msg = "회원가입 되었습니다.";
        } else if (res == 2) {
            msg = "이미 가입된 아이디입니다.";
        } else if (res == 3){
            msg = "오류로 인해 회원가입이 실패하였습니다.";
        }

        MsgDTO rmsg = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();

        log.info("리턴 값 : " + rmsg);
        log.info(this.getClass().getName()  + ".insertUserInfo End! (회원가입)");

        return rmsg;
    }

    // 아이디 중복체크
    @ResponseBody
    @PostMapping(value = "getUserIdExists")
    public UserInfoDTO getUserIdExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start! (아이디 중복체크)");

        String userId = CmmUtil.nvl(request.getParameter("userId"));

        log.info("userId : " + userId);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .build();

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserIdExists(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + ".getUserIdExists End! (아이디 중복체크)");

        return rDTO;
    }

    // 이메일 중복체크
    @ResponseBody
    @PostMapping(value = "getUserEmailExists")
    public UserInfoDTO getUserEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserEmailExists Start! (이메일 중복체크)");


        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("email : " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userEmail(email)
                .build();


        //이메일을 통해 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserEmailExists(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + ".getUserEmailExists End! (이메일 중복체크)");


        return rDTO;
    }

    //별명 중복체크
    @ResponseBody
    @PostMapping(value = "getUserNickExists")
    public UserInfoDTO getUserNickExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getUserNickExists Start! (별명 중복체크)");

        String nick = CmmUtil.nvl(request.getParameter("nick"));

        log.info("nick : " + nick);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userNickname(nick)
                .build();

        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getUserNickExists(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + ".getUserNickExists End! (별명 중복체크)");

        return rDTO;
    }

    @GetMapping(value = "login")
    public String login() {
        log.info(this.getClass().getName() + ".user/login start (로그인 페이지)");
        log.info(this.getClass().getName() + ".user/login End! (로그인 페이지)");

        return "user/login";
    }
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".loginProc start (로그인 로직 실행)");

        String msg;

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String password = CmmUtil.nvl(request.getParameter("pwd"));

        log.info("user_id : "  + userId);
        log.info("password : " + password);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .userPassword(password).build();

        int res = userInfoService.getUserLogin(pDTO);

        log.info("res : " + res);

        if (res == 1) {
            msg = "로그인이 성공했습니다.";
            session.setAttribute("SS_USER_ID", userId);
        } else {
            msg = "아이디와 비밀번호가 올바르지 않습니다.";
        }

        MsgDTO dto = MsgDTO.builder()
                .res(res)
                .msg(msg)
                .build();
        log.info(this.getClass().getName() + ".loginProc End! (로그인 로직 실행)");

        return dto;
    }


    //아이디 찾기
    @ResponseBody
    @PostMapping(value = "searchUserId")
    public MsgDTO searchUserId(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".searchUserId Start! (아이디 찾기)");

        String msg;

        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("userName : " + userName);
        log.info("email : " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userName(userName)
                .userEmail(email)
                .build();

        String res = userInfoService.searchUserIdProc(pDTO);

        // 추가된 로그
        log.info("res: " + res);

        if (!Objects.equals(res, "")) {
            msg = userName + " 회원님의 아이디는 [ " + res + " ] 입니다.";
        } else {
            msg = "회원정보가 일치하지 않습니다.";
        }

        MsgDTO dto = MsgDTO.builder().msg(msg).build();

        log.info(this.getClass().getName() + ".searchUserId End! (아이디 찾기)");

        return dto;
    }

    @ResponseBody
    @PostMapping(value = "getEmailAuthNumber")
    public UserInfoDTO getEmailAuthNumber(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailAuthNumber Start! (이메일 중복체크)");


        String email = CmmUtil.nvl(request.getParameter("email")); // 이메일

        log.info("email : " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userEmail(email)
                .build();


        //이메일을 통해 중복된 이메일인지 조회
        UserInfoDTO rDTO = Optional.ofNullable(userInfoService.getEmailAuthNumber(pDTO))
                .orElseGet(() -> UserInfoDTO.builder().build());

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + ".getEmailAuthNumber End! (이메일 중복체크)");


        return rDTO;
    }


    /**
     * 비밀번호 재설정 화면
     */
    @GetMapping(value = "searchPassword")
    public String searchPassword(HttpSession session) {
        log.info(this.getClass().getName() + ".user/searchPassword Start! (비밀번호 찾기)");

        // 강제 URL 입력 등 오는 경우가 있어 세션 삭제
        // 비밀번호 재생성하는 화면은 보안을 위해 생성한 NEW_PASSWORD 세션 삭제
        session.setAttribute("NEW_PASSWORD", "");
        session.removeAttribute("NEW_PASSWORD");

        log.info(this.getClass().getName() + ".user/searchPassword End! (비밀번호 찾기)");

        return "user/searchPassword";
    }

    @GetMapping(value = "newPassword")
    public String newPassword(HttpSession session) {
        log.info(this.getClass().getName() + ".Get-newPassword Start! (비밀번호 재설정)");
//
//        // 강제 URL 입력 등 오는 경우가 있어 세션 삭제
//        // 비밀번호 재생성하는 화면은 보안을 위해 생성한 NEW_PASSWORD 세션 삭제
//        session.setAttribute("NEW_PASSWORD", "");
//        session.removeAttribute("NEW_PASSWORD");

        log.info(this.getClass().getName() + ".Get-newPassword End! (비밀번호 재설정)");

        return "user/newPassword";
    }
    // 비밀번호 찾기
    @ResponseBody
    @PostMapping(value = "newPassword", produces = "application/json; charset=UTF-8")
    public MsgDTO newPassword(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".Post-newPassword Start! (비밀번호 재설정)");

        String msg = "";
        MsgDTO dto;

        // 세션에서 userId 받아오기
        String userId = (String) session.getAttribute("NEW_PASSWORD_USER_ID");

        if (userId == null || userId.isEmpty()) {
            msg = "세션이 만료되었습니다. 다시 비밀번호 찾기를 진행해주세요.";
            dto = MsgDTO.builder().msg("false").build(); // 비밀번호를 찾지 못한 경우
            return dto; // 클라이언트로 바로 JSON 반환
        }

        // 새 비밀번호 받아오기
        String password = CmmUtil.nvl(request.getParameter("password"));

        log.info("Received userId: " + userId);
        log.info("Received password: " + password);

        // 신규 비밀번호를 해시로 암호화
        String hashedPassword = EncryptUtil.encHashSHA256(password);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .userPassword(hashedPassword)
                .build();

        // 서비스로 DTO 전달
        String res = userInfoService.newPasswordProc(pDTO);
        if (res == "success") {

            // 비밀번호 재생성 후 세션 삭제
            session.removeAttribute("NEW_PASSWORD_USER_ID");

            msg = "비밀번호가 재설정되었습니다.";

            dto = MsgDTO.builder().msg("success").build(); // 성공적으로 비밀번호를 재설정한 경우
            log.info(this.getClass().getName() + ".Post-newPassword End!");

            log.info("dto : " + dto);
            return dto;
        } else {
            dto = MsgDTO.builder().msg("false").build(); // 성공적으로 비밀번호를 재설정한 경우
            msg = "오류가 발생하였습니다.";
            log.info(this.getClass().getName() + ".user/newPassword End! (비밀번호 재생성)");
            return dto;
        }
    }

    @ResponseBody
    @PostMapping(value = "searchPasswordProc")
    public MsgDTO searchPasswordProc(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".searchPasswordProc Start! (비밀번호 찾기)");

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("userId: " + userId);
        log.info("email: " + email);
        log.info("userName: " + userName);


        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userId(userId)
                .userName(userName)
                .userEmail(email)
                .build();

        String password = userInfoService.searchPasswordProc(pDTO);

        log.info("Retrieved password: " + password);

        MsgDTO dto;
        if (password != null && !password.isEmpty()) {
            session.setAttribute("NEW_PASSWORD_USER_ID", userId); // 성공적으로 비밀번호를 찾은 경우 세션에 아이디 저장
            dto = MsgDTO.builder().msg("success").build(); // 성공적으로 비밀번호를 찾은 경우
        } else {
            dto = MsgDTO.builder().msg("fail").build(); // 비밀번호를 찾지 못한 경우
        }

        log.info(this.getClass().getName() + ".searchPasswordProc end! (비밀번호 찾기)");
        return dto;
    }

    @GetMapping(value = "myPage")
    public String getMyPage() {
        log.info(this.getClass().getName() + ".getMyPage Start (마이페이지)");
        log.info(this.getClass().getName() + ".getMyPage end (마이페이지)");
        // main 메서드 로직
        return "user/myPage";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
    }
}
