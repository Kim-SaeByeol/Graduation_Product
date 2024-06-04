package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value =  "/social")
public class SocialController {

    /**
     * 로그인
     */
    @GetMapping(value = "/socialRegForm")
    public String login() {
        log.info(this.getClass().getName() + "로그인 페이지");
        return "social/socialRegForm";
    }
}
