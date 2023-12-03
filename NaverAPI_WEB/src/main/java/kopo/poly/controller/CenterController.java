package kopo.poly.controller;

import kopo.poly.dto.CenterDTO;
import kopo.poly.dto.GeocodingDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@RequestMapping(value = "/center")
@RequiredArgsConstructor
@Controller
public class CenterController {

    private final ICenterService centerService;
    private final IGeocodingService geocodingService;

    @GetMapping(value = "center")
    public String CenterList(HttpSession session, ModelMap model, @RequestParam(defaultValue = "1") int page) throws Exception {
        log.info(this.getClass().getName() + ".noticeList Start!");
        
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


        log.info(this.getClass().getName() + ".noticeList End!");
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
        log.info(this.getClass().getName() + ".noticeReg Start!");
        log.info(this.getClass().getName() + ".noticeReg End!");
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
            GeocodingDTO gDTO = new GeocodingDTO();


            pDTO.setRegion(region);
            pDTO.setCenterName(centerName);
            pDTO.setAddress(address);
            pDTO.setPhone(phone);

            gDTO.setAddress(address);


            // geocoding 을 실행하는 코드
            geocodingService.Geocoding(gDTO);
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