package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/main")
@Controller
public class MainController {

    @GetMapping(value = "/main")
    public String main() {
        log.info(this.getClass().getName() + ".main Start");
        // main 메서드 로직
        return "main/main";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
    }
}