package kopo.poly.Controller;


import kopo.poly.DTO.CenterDTO;
import kopo.poly.DTO.MsgDTO;
import kopo.poly.Service.IHelp_CenterService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping(value = "/center")
@RequiredArgsConstructor
@Controller
public class Help_CenterController {
    private final IHelp_CenterService centerService;

    @GetMapping(value = "Help_centerReg")
    public String Help_centerReg(HttpServletRequest request, HttpSession session, ModelMap model) {
        log.info(this.getClass().getName() + ".Help_centerReg에 들어왔음.");
        /**
         * centerReg.html 파일에 selectedInsertType에 값을 넣어 보내
         *  하나의 html 로 3개의 Center들의 Insert 를 담당함.
         **/
        model.addAttribute("selectedInsertType", "Help");
        return "/center/centerReg";
    }

    @ResponseBody
    @PostMapping(value = "Help_centerInsert")
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

}
