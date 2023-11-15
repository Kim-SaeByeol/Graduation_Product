package kopo.poly.Controller;



import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}