package poly.graduation_products.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import poly.graduation_products.dto.YoutubeDTO;
import poly.graduation_products.service.IYoutubeService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/index")
@Controller
public class IndexController {
    private final IYoutubeService youtubeService;

    @GetMapping(value = "index")
    public String index(ModelMap model) throws Exception {
        log.info(this.getClass().getName() + "메인 페이지");

        List<YoutubeDTO> yList = youtubeService.getYoutueInfo();
        model.addAttribute("yList", yList);

        // main 메서드 로직
        return "index/index";  // 이 부분을 "Main/main"에서 "Main/main.html"로 수정
    }
}