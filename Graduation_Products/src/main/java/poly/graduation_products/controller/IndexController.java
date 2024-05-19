package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/index")
@Controller
public class IndexController {

    @GetMapping(value = "index")
    public String index() {
        log.info(this.getClass().getName() + ".index Start");
        log.info(this.getClass().getName() + ".index end");
        // main 메서드 로직
        return "index/index";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
    }
}