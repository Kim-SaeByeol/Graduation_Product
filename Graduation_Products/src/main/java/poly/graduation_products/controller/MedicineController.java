package poly.graduation_products.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.graduation_products.dto.MedicineDTO;
import poly.graduation_products.service.IMedicineService;
import poly.graduation_products.service.impl.MedicineService;
import poly.graduation_products.util.CmmUtil;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/medicine")
@Controller
public class MedicineController {

    private final MedicineService medicineService;


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

    @ResponseBody
    @PostMapping("medSearch")
    public List<MedicineDTO> medSearch(HttpServletRequest request, HttpSession session) throws Exception {
        log.info(this.getClass().getName() + ".searchMedicine Start (의약품 상세 조회)");

        String itemName = CmmUtil.nvl(request.getParameter("search"));
        log.info("itemName : " + itemName);

        MedicineDTO pDTO = MedicineDTO.builder()
                .itemName(itemName)
                .build();

        List<MedicineDTO> dto = medicineService.fetchMedicineInfo(pDTO);

        log.info("dto : " + dto);
        log.info(this.getClass().getName() + ".searchMedicine End (의약품 상세 조회)");

        return dto;
    }

}
