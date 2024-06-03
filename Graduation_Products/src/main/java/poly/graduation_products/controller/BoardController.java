package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import poly.graduation_products.util.PhotoUtil;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/board")
@Controller
public class BoardController {


        // 공지사항
        @GetMapping(value = "noticeBorad")
        public String getFreeBorad() {
            return "board/freeBorad";
        }


        // 에디터 테스트
        @GetMapping(value = "testBorad")
        public String gettestBorad() {
            log.info(this.getClass().getName() + ".gettestBorad Start (에디터 테스트)");
            log.info(this.getClass().getName() + ".gettestBorad end (에디터 테스트)");

            return "board/testBorad";
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