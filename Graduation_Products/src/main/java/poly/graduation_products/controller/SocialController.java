package poly.graduation_products.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poly.graduation_products.dto.MsgDTO;
import poly.graduation_products.dto.SocialDTO;
import poly.graduation_products.dto.TagDTO;
import poly.graduation_products.service.impl.SocialService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.util.EncryptUtil;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value =  "/social")
public class SocialController {

    private final SocialService socialService;

    /**
     * 회원가입
     */
    @GetMapping(value = "/socialRegForm")
    public String socialRegForm(HttpSession session) {
        log.info(this.getClass().getName() + "소셜 회원가입 페이지");

        String Check = (String) session.getAttribute("Check");
        session.removeAttribute("Check");


        if (Check == null) {
            log.info("세션 값이 존재하지 않음 Check : " + Check);
            return "redirect:/user/login";
        }

        log.info("세션 값이 존재함 Check : " + Check);
        return "social/socialRegForm";
    }

    @ResponseBody
    @PostMapping(value = "socialRegForm")
    public MsgDTO insertSocial(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName()  + "소셜 회원가입 실행");

        String userEmail = CmmUtil.nvl(request.getParameter("email"));
        String nickname = CmmUtil.nvl(request.getParameter("nick"));

        log.info("userEmail : " + userEmail);
        log.info("nickname : " + nickname);

        // 세션에서 값 가져오기
        String socialEmail = (String)request.getSession().getAttribute("email");
        String provider = (String)request.getSession().getAttribute("provider");
        String picture = (String)request.getSession().getAttribute("picture");

        log.info(this.getClass().getName() + "socialEmail : " + socialEmail);
        log.info(this.getClass().getName() + "provider : " + provider);
        log.info(this.getClass().getName() + "picture : " + picture);

        socialService.insertSocialInfo(nickname, userEmail, picture, socialEmail, provider);

        String msg = "회원가입 되셨습니다.";

        MsgDTO rDTO = MsgDTO.builder()
                .msg(msg)
                .build();

        session.setAttribute("SS_USER_NAME", nickname); // 세션에 이름 저장

        log.info(this.getClass().getName()  + "회원가입 종료" );

        return rDTO;
    }

}

