package kopo.poly.Controller;



import kopo.poly.DTO.CenterDTO;
import kopo.poly.DTO.MsgDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequestMapping(value = "/center")
@RequiredArgsConstructor
@Controller
public class CT_CenterController {

    private final ICenterService centerService;

    
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
        int startIndex = (page - 1) * noticesPerPage;
        int endIndex = Math.min(startIndex + noticesPerPage, allNotices.size());
        return allNotices.subList(startIndex, endIndex);
    }

    @GetMapping(value = "CT_centerReg")
    public String centerReg(HttpServletRequest request, HttpSession session) {
        return "/center/CT_centerReg";
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
    public String searchCenter(HttpServletRequest request, HttpSession session, ModelMap model,
                               @RequestParam(value = "is_sido", required = false) String isSido,
                               @RequestParam(value = "centerAddress", required = false) String centerAddress,
                               @RequestParam(defaultValue = "1") int page) {
        log.info(this.getClass().getName() + ".searchCenter Start!");

        try {
            List<CenterDTO> searchResults;

            if (isSido != null && centerAddress != null) {
                // 도시와 주소가 함께 입력된 경우
                searchResults = centerService.searchCenter_all(isSido, centerAddress);
            } else if (isSido != null) {
                // 도시만 입력된 경우
                searchResults = centerService.searchCenter_sido(isSido);
            } else if (centerAddress != null) {
                // 주소만 입력된 경우
                searchResults = centerService.searchCenter_address(centerAddress);
            } else {
                // 검색 조건이 없는 경우 전체 리스트 가져오기
                searchResults = centerService.getCenterList();
            }

            // 페이징 처리
            int totalResults = searchResults.size();
            int resultsPerPage = 5; // 페이지당 표시할 결과 수 (원하는 값으로 수정)
            int totalPages = (int) Math.ceil((double) totalResults / resultsPerPage);

            // 페이징된 결과 리스트 가져오기
            List<CenterDTO> pagedList = getPagedList(searchResults, page, resultsPerPage);

            model.addAttribute( "searchResults", pagedList);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", page);

        } catch (Exception e) {
            log.error("검색 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        log.info(this.getClass().getName() + ".searchCenter End!");
        return "/center/CT_Search";
    }

}