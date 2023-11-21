package kopo.poly.Controller;



import kopo.poly.DTO.CenterDTO;
import kopo.poly.DTO.MsgDTO;
import kopo.poly.Service.ICT_CenterService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Slf4j
@RequestMapping(value = "/center")
@RequiredArgsConstructor
@Controller
public class CT_CenterController {

    private final ICT_CenterService centerService;


    // 조회 기능
    @GetMapping(value = "CT_Center")
    public String CT_CenterList(HttpSession session, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
        log.info(this.getClass().getName() + ".CT_CenterList Start!");
        session.setAttribute("SESSION_USER_ID", "USER01");

        // 공지사항 리스트 가져오기
        List<CenterDTO> rList = Optional.ofNullable(centerService.getCenterList())
                .orElseGet(ArrayList::new);

        // 페이징 처리
        int totalCenter = rList.size();
        int CenterPerPage = 5; // 페이지당 표시할 공지사항 수 (원하는 값으로 수정)
        int totalPages = (int) Math.ceil((double) totalCenter / CenterPerPage);

        // 페이징된 공지사항 리스트 가져오기
        List<CenterDTO> pagedList = getPagedList(rList, page, CenterPerPage);

        model.addAttribute("rList", pagedList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        log.info(this.getClass().getName() + ".CT_CenterList End!");
        return "/center/CT_Center";
    }

    // 페이지 번호에 따른 공지사항 리스트 가져오기
    private List<CenterDTO> getPagedList(List<CenterDTO> allNotices, int page, int noticesPerPage) {
        log.info(this.getClass().getName() + ".getPagedList Start!");
        int startIndex = (page - 1) * noticesPerPage;
        int endIndex = Math.min(startIndex + noticesPerPage, allNotices.size());
        log.info(this.getClass().getName() + ".getPagedList End!");
        return allNotices.subList(startIndex, endIndex);
    }

    @GetMapping(value = "CT_centerReg")
    public String CT_centerReg(HttpServletRequest request, HttpSession session, ModelMap model) {
        log.info(this.getClass().getName() + ".CT_centerReg에 들어왔음.");
        /**
         * centerReg.html 파일에 selectedInsertType에 값을 넣어 보내
         *  하나의 html 로 3개의 Center들의 Insert 를 담당함.
         **/
         model.addAttribute("selectedInsertType", "CT");
        return "/center/centerReg";
    }



    @ResponseBody
    @PostMapping(value = "CT_centerInsert")
    public MsgDTO CenterInsert(HttpServletRequest request, HttpSession session) {
        log.info(this.getClass().getName() + ".CenterInsert Start!");
        String msg = "";
        MsgDTO dto = null;
        try {
            String region = CmmUtil.nvl(request.getParameter("region"));
            String centerName = CmmUtil.nvl(request.getParameter("centerName"));
            String address = CmmUtil.nvl(request.getParameter("address"));
            String phone = CmmUtil.nvl(request.getParameter("phone"));
            log.info("region : " + region);
            log.info("centerName : " + centerName);
            log.info("address : " + address);
            log.info("phone : " + phone);

            CenterDTO pDTO = new CenterDTO();

            pDTO.setRegion(region);
            pDTO.setCenterName(centerName);
            pDTO.setAddress(address);
            pDTO.setPhone(phone);

            centerService.Geocoding(pDTO);
            centerService.insertCenterInfo(pDTO);

            msg = "등록되었습니다.";
        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);
            log.info(this.getClass().getName() + ".CenterInsert End!");
        }
        return dto;
    }


    // 검색기능
    @GetMapping("/CT_Search")
    @ResponseBody
    public Map<String, Object> searchCenter(HttpServletRequest request, HttpSession session, ModelMap model,
                                            @RequestParam(value = "is_sido", required = false) String isSido,
                                            @RequestParam(value = "searchText", required = false) String searchText,
                                            @RequestParam(defaultValue = "1") int page) {
        log.info(this.getClass().getName() + ".searchCenter Start!");

        log.info("is_sido : " + isSido);
        log.info("searchText : " + searchText);




        Map<String, Object> response = new HashMap<>();

        try {
            List<CenterDTO> searchResults;

            if (!isSido.equals("-1") && !searchText.equals("-1")) {
                // 도시와 주소가 함께 입력된 경우
                searchResults = centerService.searchCenter_all(isSido, searchText);
            } else if (!isSido.equals("-1")) {
                // 도시만 입력된 경우
                log.info("도시 입력 완료");
                searchResults = centerService.searchCenter_sido(isSido);
            } else if (!searchText.equals("-1")) {
                // 주소만 입력된 경우
                log.info("주소 입력 완료");
                searchResults = centerService.searchCenter_address(searchText);
            } else {
                // 검색 조건이 없는 경우 전체 리스트 가져오기
                log.info("아무 조건 없이 검색 됨.");
                searchResults = centerService.getCenterList();
            }


            log.info("페이징 시작");

            // 페이징 처리
            int totalResults = searchResults.size();
            int resultsPerPage = 5; // 페이지당 표시할 결과 수 (원하는 값으로 수정)
            int totalPages = (int) Math.ceil((double) totalResults / resultsPerPage);

            // 페이징된 결과 리스트 가져오기
            List<CenterDTO> pagedList = getPagedList(searchResults, page, resultsPerPage);

            response.put("searchResults", pagedList);
            response.put("totalPages", totalPages);
            response.put("currentPage", page);
            response.put("success", true);

            log.info("searchResults : " + searchResults);
            log.info("pagedList : " + pagedList);
            log.info("totalPages : " + totalPages);
            log.info("page : " + page);

        } catch (Exception e) {
            log.error("검색 중 오류 발생: " + e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        log.info(this.getClass().getName() + ".searchCenter End!");
        return response;
    }


}