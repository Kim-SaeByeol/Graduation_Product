package poly.graduation_products.controller;

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


    //회원가입 폼
    @GetMapping(value = "userRegForm")
    public String userRegForm() {

        log.info(this.getClass().getName() + ".userRegForm Start!(회원가입 화면)");
        log.info(this.getClass().getName() + ".userRegForm End!(회원가입 화면)");

        return "user/userRegForm";
    }

    //회원가입하기
    @ResponseBody
    @PostMapping(value = "insertUserInfo")
    public MsgDTO insertUserInfo(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName()  + ".insertUserInfo Start! (회원가입)");

        String msg = null;
        String userId = CmmUtil.nvl(request.getParameter("userId"));
        // 비밀번호는 개인정보인 만큼 암호화를 진행
        String pwd = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("pwd")));
        // 이메일은 개인정보인 만큼 암호화를 진행
        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));
        String name = CmmUtil.nvl(request.getParameter("userName"));
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

        // 테스트 이니 이후 삭제할 것.
        log.info("테스트 이메일 확인 (*삭제필): " + CmmUtil.nvl(request.getParameter("email")));

        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));

        log.info("email : " + email);

        UserInfoDTO pDTO = UserInfoDTO.builder()
                .userEmail(email)
                .build();

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

        log.info(this.getClass().getName() + ".getUserNickExists End! (별명 중복체크)");

        return rDTO;
    }
}
