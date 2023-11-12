package kopo.poly.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping(value = "/Main")
    public String main() {
        log.info(this.getClass().getName() + ".main Start");
        try {
            // main 메서드 로직
            return "Main/main";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
        } catch (Exception e) {
            log.error("Error in main method", e);
            throw e; // 예외 다시 던지기 (에러 페이지로 이동)
        } finally {
            log.info(this.getClass().getName() + ".main End");
        }
    }



    @GetMapping(value = "/centermain")
    public String centermain() {
        log.info(this.getClass().getName() + ".centermain Start");
        return "centermain";
    }
}