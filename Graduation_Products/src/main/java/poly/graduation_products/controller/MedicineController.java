package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/medicine")
@Controller
public class MedicineController {

    // 일반 의약품
    @GetMapping(value = "otcDrugs")
    public String getOtcDrugs() {
        log.info(this.getClass().getName() + ".getOtcDrugs Start (일반 의약품)");
        log.info(this.getClass().getName() + ".getOtcDrugs end (일반 의약품)");

        return "medicine/otcDrugs";
    }

    // 의약품 복용법
    @GetMapping(value = "medDosage")
    public String getMedDosage() {
        log.info(this.getClass().getName() + ".getMedDosage Start (의약품 복용법)");
        log.info(this.getClass().getName() + ".getMedDosage end (의약품 복용법)");

        return "medicine/medDosage";
    }

    // 외용약 사용법
    @GetMapping(value = "topicalUsage")
    public String getTopicalUsage() {
        log.info(this.getClass().getName() + ".getTopicalUsage Start (외용약 사용법)");
        log.info(this.getClass().getName() + ".getTopicalUsage end (외용약 사용법)");

        return "medicine/topicalUsage";
    }

    // 의약품 상세검색
    @GetMapping(value = "medSearch")
    public String getMedSearch() {
        log.info(this.getClass().getName() + ".getMedSearch Start (의약품 상세검색)");
        log.info(this.getClass().getName() + ".getMedSearch end (의약품 상세검색)");

        return "medicine/medSearch";
    }



}
