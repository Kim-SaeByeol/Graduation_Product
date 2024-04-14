package controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.IUserInfoService;

@Slf4j
@RequiredArgsConstructor
@RequestMapping (value = "/user")
@Controller
public class UserInfoController {

    private final IUserInfoService userInfoService;

    @GetMapping(value = "CareTrack_LOGIN_002")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".userRegForm Start (회원가입)");
        log.info(this.getClass().getName() + ".userRegForm End (회원가입)");
    return "user/CareTrack_LOGIN_002";
    }

    @RequestBody
    @PostMapping
}
