package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/customerSvc")
@Controller
public class CustomerSvcController {

    @GetMapping(value = "FAQ")
    public String getFAQ() {
        log.info(this.getClass().getName() + ".getFAQ Start (자주 묻는 질문)");
        log.info(this.getClass().getName() + ".getFAQ end (자주 묻는 질문)");
        // main 메서드 로직
        return "customerSvc/FAQ";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
    }

}
