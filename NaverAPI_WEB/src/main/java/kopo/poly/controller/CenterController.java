package kopo.poly.controller;

import kopo.poly.dto.CenterDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.ICenterService;
import kopo.poly.service.IGeocodingService;
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
public class CenterController {

    private final ICenterService centerService;
    private final IGeocodingService geocodingService;

    @GetMapping(value = "center")
    public String CenterList(HttpSession session, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
        log.info(this.getClass().getName() + ".CenterList Start!");
        
//
//        //수정해야할 부분
//        session.setAttribute("SESSION_USER_ID", "USER01");
//        //


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


        log.info(this.getClass().getName() + ".CenterList End!");
        return "/center/center";
    }

    // 페이지 번호에 따른 공지사항 리스트 가져오기
    private List<CenterDTO> getPagedList(List<CenterDTO> allNotices, int page, int noticesPerPage) {
        int startIndex = (page - 1) * noticesPerPage;
        int endIndex = Math.min(startIndex + noticesPerPage, allNotices.size());
        return allNotices.subList(startIndex, endIndex);
    }


    @GetMapping(value = "centerReg")
    public String centerReg() {
        log.info(this.getClass().getName() + ".centerReg Start!");
        log.info(this.getClass().getName() + ".centerReg End!");
        return "/center/centerReg";
    }


    @ResponseBody
    @PostMapping(value = "centerInsert")
    public MsgDTO CenterInsert(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".CenterInsert Start!");
        String msg = "";
        MsgDTO dto = null;
        try {
            //            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
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

            // geocoding 을 실행하는 코드
            geocodingService.Geocoding(pDTO);

            log.info("현재 CenterDTO 에 저장된 값들 ");
            log.info("region : " + pDTO.getRegion());
            log.info("centerName : " + pDTO.getCenterName());
            log.info("address : " + pDTO.getAddress());
            log.info("phone : " + pDTO.getPhone());
            log.info("XAddress : " + pDTO.getXAddress());
            log.info("YAddress : " + pDTO.getYAddress());


            // 값을 DB에 저장하는 코드
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

    @GetMapping("/Search")
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

        } catch (Exception e) {
            log.error("검색 중 오류 발생: " + e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        log.info(this.getClass().getName() + ".searchCenter End!");
        return response;
    }



//
//
//    // 센터 정보 상세보기 폼
//    // 수정 필요
//    @GetMapping(value = "centerInfo")
//    public String centerInfo(HttpServletRequest request, ModelMap model) throws Exception {
//        log.info(this.getClass().getName() + ".centerInfo Start!");
//        log.info(this.getClass().getName() + ".centerInfo End!");
//        return "/center/centerInfo";
//    }
//
//    // 센터 정보 수정하기 폼
//    // 수정 필요
//    @GetMapping(value = "centerEditInfo")
//    public String centerEditInfo(HttpServletRequest request, ModelMap model) throws Exception {
//        log.info(this.getClass().getName() + ".centerEditInfo Start!");
//        log.info(this.getClass().getName() + ".centerEditInfo End!");
//        return "/center/centerEditInfo";
//    }
//
//    @ResponseBody
//    @PostMapping(value = "noticeUpdate")
//    public MsgDTO noticeUpdate(HttpSession session, HttpServletRequest request) {
//        log.info(this.getClass().getName() + ".noticeUpdate Start!");
//        String msg = "";
//        MsgDTO dto = null;
//        try {
//            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID"));
//            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
//            String title = CmmUtil.nvl(request.getParameter("title"));
//            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn"));
//            String contents = CmmUtil.nvl(request.getParameter("contents"));
//            log.info("userId : " + userId);
//            log.info("nSeq : " + nSeq);
//            log.info("title : " + title);
//            log.info("noticeYn : " + noticeYn);
//            log.info("contents : " + contents);
//            NoticeDTO pDTO = new NoticeDTO();
//            pDTO.setUserId(userId);
//            pDTO.setNoticeSeq(nSeq);
//            pDTO.setTitle(title);
//            pDTO.setNoticeYn(noticeYn);
//            pDTO.setContents(contents);
//            noticeService.updateNoticeInfo(pDTO);
//            msg = "수정되었습니다.";
//        } catch (Exception e) {
//            msg = "실패하였습니다. : " + e.getMessage();
//            log.info(e.toString());
//            e.printStackTrace();
//        } finally {
//            dto = new MsgDTO();
//            dto.setMsg(msg);
//            log.info(this.getClass().getName() + ".noticeUpdate End!");
//        }
//        return dto;
//    }
//
//    @ResponseBody
//    @PostMapping(value = "noticeDelete")
//    public MsgDTO noticeDelete(HttpServletRequest request) {
//        log.info(this.getClass().getName() + ".noticeDelete Start!");
//        String msg = "";
//        MsgDTO dto = null;
//        try {
//            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"));
//            log.info("nSeq : " + nSeq);
//            NoticeDTO pDTO = new NoticeDTO();
//            pDTO.setNoticeSeq(nSeq);
//            noticeService.deleteNoticeInfo(pDTO);
//            msg = "삭제되었습니다.";
//        } catch (Exception e) {
//            msg = "실패하였습니다. : " + e.getMessage();
//            log.info(e.toString());
//            e.printStackTrace();
//        } finally {
//            dto = new MsgDTO();
//            dto.setMsg(msg);
//            log.info(this.getClass().getName() + ".noticeDelete End!");
//        }
//        return dto;
//    }

}