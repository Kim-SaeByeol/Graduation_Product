package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/board")
@Controller
public class BoardController {

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

}
