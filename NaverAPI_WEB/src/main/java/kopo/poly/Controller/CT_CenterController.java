package kopo.poly.Controller;


import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.Service.impl.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "/CT_Center")
    public String CenterList(HttpSession session, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".CenterList Start!");
        //해당 부분에 세션으로 부터 로그인 값을 받아와야함.
        /**
         * // 세션에서 사용자 정보 가져오기
         *     UserInfoDTO userInfo = (UserInfoDTO) session.getAttribute("SESSION_USER_INFO");
         *
         *     // 세션에 정보가 없으면 DB에서 가져오기
         *     if (userInfo == null) {
         *         userInfo = userInfoService.getUserInfoByUserId(userId);
         *
         *         // 세션에 정보가 없는 경우 기본값으로 설정
         *         if (userInfo == null) {
         *             userInfo = new UserInfoDTO();
         *             userInfo.setUserId(userId);
         *             userInfo.setUserName("Guest");
         *             userInfo.setLoggedIn(false); // 로그인하지 않은 상태로 설정
         *         }
         *     }
         */
        List<CenterDTO> rList = Optional.ofNullable(centerService.getCenterList())
                .orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);


        log.info(this.getClass().getName() + ".CenterList End!");

        return "center/CT_Center";
    }
}