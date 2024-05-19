package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/gallery")
@Controller
public class GalleryController {

    // 카드뉴스
    @GetMapping(value = "cardNews")
    public String getcardNews() {
        log.info(this.getClass().getName() + ".getcardNews Start (카드뉴스)");
        log.info(this.getClass().getName() + ".getcardNews end (카드뉴스)");

        return "gallery/cardNews";
    }


    // 건강관리
    @GetMapping(value = "healthCare")
    public String gethealthCare() {
        log.info(this.getClass().getName() + ".gethealthCare Start (건강관리)");
        log.info(this.getClass().getName() + ".gethealthCare end (건강관리)");

        return "gallery/healthCare";
    }

    // 질병예방
    @GetMapping(value = "DP")
    public String getDP() {
        log.info(this.getClass().getName() + ".getDP Start (질병예방)");
        log.info(this.getClass().getName() + ".getDP end (질병예방)");

        return "gallery/DP";
    }

    @GetMapping(value = "page_lwdff23j")
    public String page_lwdff23j() {
        log.info(this.getClass().getName() + ".getDP Start (질병예방)");
        log.info(this.getClass().getName() + ".getDP end (질병예방)");

        return "gallery/page_lwdff23j";
    }
}
