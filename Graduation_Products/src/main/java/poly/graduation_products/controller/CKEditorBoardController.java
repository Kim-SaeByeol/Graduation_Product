package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import poly.graduation_products.dto.CKEditorBoardDTO;
import poly.graduation_products.util.PhotoUtil;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/board")
@Controller
public class CKEditorBoardController {


    // 공지사항
    @GetMapping(value = "noticeBorad")
    public String getNoticeBorad() {
        log.info(this.getClass().getName() + ".getNoticeBorad Start (공지사항)");
        log.info(this.getClass().getName() + ".getNoticeBorad end (공지사항)");

        return "board/noticeBorad";
    }
    // 건강 게시판
    @GetMapping(value = "healthBoard")
    public String getHealthBoard() {
        log.info(this.getClass().getName() + ".getHealthBoard Start (건강 게시판)");
        log.info(this.getClass().getName() + ".getHealthBoard end (건강 게시판)");

        return "board/healthBoard";
    }

    // 질문 게시판
    @GetMapping(value = "questionBorad")
    public String getQuestionBorad() {
        log.info(this.getClass().getName() + ".getQuestionBorad Start (질문 게시판)");
        log.info(this.getClass().getName() + ".getQuestionBorad end (질문 게시판)");

        return "board/questionBorad";
    }

    // 자유 게시판
    @GetMapping(value = "freeBorad")
    public String getFreeBorad() {
        log.info(this.getClass().getName() + ".getFreeBorad Start (자유 게시판)");
        log.info(this.getClass().getName() + ".getFreeBorad end (자유 게시판)");

        return "board/freeBorad";
    }


    // 에디터 테스트
    @GetMapping(value = "testBorad")
    public String gettestBorad() {
        log.info(this.getClass().getName() + ".gettestBorad Start (에디터 테스트)");
        log.info(this.getClass().getName() + ".gettestBorad end (에디터 테스트)");

        return "board/testBorad";
    }

    @PostMapping("/testBorad")
    public String testBorad(CKEditorBoardDTO saveDTO) {

        log.info("테스트 POST 실행");

        System.out.println(saveDTO);

        return "redirect:/";
    }

    @PostMapping("upload")
    public ModelAndView upload(MultipartHttpServletRequest request) {
        ModelAndView mav = new ModelAndView("jsonView");

        String uploadPath = PhotoUtil.ckUpload(request);

        mav.addObject("uploaded", true);
        mav.addObject("url", uploadPath);
        return mav;
    }

}
