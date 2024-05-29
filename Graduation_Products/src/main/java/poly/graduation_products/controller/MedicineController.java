package poly.graduation_products.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.graduation_products.dto.MedicineDTO;
import poly.graduation_products.service.impl.MedicineService;
import poly.graduation_products.util.CmmUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/medicine")
@Controller
public class MedicineController {

    private final MedicineService medicineService;


    // 일반 의약품
    @GetMapping(value = "/otcDrugs")
    public String getOtcDrugs() {
        log.info(this.getClass().getName() + ".getOtcDrugs Start (일반 의약품)");
        log.info(this.getClass().getName() + ".getOtcDrugs end (일반 의약품)");

        return "medicine/otcDrugs";
    }

    // 의약품 복용법
    @GetMapping(value = "/medDosage")
    public String getMedDosage() {
        log.info(this.getClass().getName() + ".getMedDosage Start (의약품 복용법)");
        log.info(this.getClass().getName() + ".getMedDosage end (의약품 복용법)");

        return "medicine/medDosage";
    }

    // 외용약 사용법
    @GetMapping(value = "/topicalUsage")
    public String getTopicalUsage() {
        log.info(this.getClass().getName() + ".getTopicalUsage Start (외용약 사용법)");
        log.info(this.getClass().getName() + ".getTopicalUsage end (외용약 사용법)");

        return "medicine/topicalUsage";
    }

    // 의약품 상세검색
    @GetMapping(value = "/medSearch")
    public String getMedSearch(Model model, @RequestParam(defaultValue = "1") int page) {
        log.info(this.getClass().getName() + ".getMedSearch Start (의약품 상세검색)");

        // 의약품 리스트와 페이징 정보는 비어있는 상태로 초기화
        List<MedicineDTO> emptyList = new ArrayList<>();
        int totalPages = 1; // 페이지도 0

        // 모델에 비어있는 리스트와 페이지 정보 추가
        model.addAttribute("medicineList", emptyList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("dList", emptyList);

        log.info(this.getClass().getName() + ".getMedSearch end (의약품 상세검색)");
        return "medicine/medSearch";
    }



    @PostMapping("/Search")
    @ResponseBody
    public Map<String, Object> searchCenter(HttpServletRequest request, HttpSession session, ModelMap model,
                                            @RequestParam(value = "searchType", required = false) String searchType,
                                            @RequestParam(value = "searchText", required = false) String searchText,
                                            @RequestParam(defaultValue = "1") int page) throws Exception {

        log.info(this.getClass().getName() + "의약품 API 조회 시작");

        Map<String, Object> response = new HashMap<>();

        try {
            List<MedicineDTO> searchResults = new ArrayList<>();
            if ("all".equals(searchType)){
                searchResults = medicineService.allSearch();
                log.info(this.getClass().getName() + "전체 조회");

            } else if ("itemName".equals(searchType)) {
                searchResults = medicineService.itemNameSearch(searchText);
                log.info(this.getClass().getName() + "의약품명 조회");

            } else if ("entpName".equals(searchType)) {
                searchResults = medicineService.entpNameSearch(searchText);
                log.info(this.getClass().getName() + "업체명 조회");

            } else if ("itemSeq".equals(searchType)) {
                searchResults = medicineService.itemSeqSearch(searchText);
                log.info(this.getClass().getName() + "품목기준코드 조회");
            }

            int totalResults = searchResults.size();
            int resultsPerPage = 10;
            int totalPages = (int) Math.ceil((double) totalResults / resultsPerPage);

            List<MedicineDTO> pagedList = getPagedList(searchResults, page, resultsPerPage);

            response.put("searchResults", pagedList);
            response.put("totalPages", totalPages);
            response.put("currentPage", page);
            response.put("success", true);

        } catch (Exception e) {
            log.error("검색 중 오류 발생: " + e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        log.info(this.getClass().getName() + "의약품 API 조회 종료");
        return response;
    }

    private List<MedicineDTO> getPagedList(List<MedicineDTO> allData, int page, int resultsPerPage) {
        int startIndex = (page - 1) * resultsPerPage;
        int endIndex = Math.min(startIndex + resultsPerPage, allData.size());
        return allData.subList(startIndex, endIndex);
    }

}
