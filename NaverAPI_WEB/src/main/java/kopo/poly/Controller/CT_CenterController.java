package kopo.poly.Controller;


import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.Service.impl.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping(value = "/center")
@RequiredArgsConstructor
@Controller
public class CT_CenterController {

    private final ICenterService centerService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "/CT_Center")
    public String CenterList(@ModelAttribute("params") final CenterDTO params, Model model) throws Exception {
       log.info(this.getClass().getName() + ".CenterList Start!");
        List<CenterDTO> rList = centerService.findAllPost(params);
        model.addAttribute("rList", rList);
       return "center/CT_Center";
    }
}