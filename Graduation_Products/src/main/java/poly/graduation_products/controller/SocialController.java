package poly.graduation_products.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.graduation_products.dto.MsgDTO;
import poly.graduation_products.util.CmmUtil;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value =  "/social")
public class SocialController {

    /**
     * 회원가입
     */
    @GetMapping(value = "/socialRegForm")
    public String socialRegForm() {
        log.info(this.getClass().getName() + "소셜 회원가입 페이지");
        return "social/socialRegForm";
    }

    public MsgDTO insertSocial(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "소셜 회원가입 시작");

        String msg = "";

        String name = CmmUtil.nvl(request.getParameter("name"));
        String nickName = CmmUtil.nvl(request.getParameter("nick"));
        String email = CmmUtil.nvl(request.getParameter("email"));


        log.info(this.getClass().getName() + "소셜 회원가입 종료");


        MsgDTO pDTO = MsgDTO.builder().build();

        return pDTO;
    }

}

