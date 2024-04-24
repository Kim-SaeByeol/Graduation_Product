package poly.graduation_products.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.graduation_products.dto.MailDTO;
import poly.graduation_products.dto.MsgDTO;
import poly.graduation_products.dto.UserInfoDTO;
import poly.graduation_products.service.IMailService;
import poly.graduation_products.service.impl.MailService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.util.RandomUtil;

import java.util.Optional;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/mail")
public class MailController {

    private final IMailService mailService;

    //이메일 인증번호 발송
    @ResponseBody
    @PostMapping(value = "sendMail")
    public MailDTO sendMail(@RequestBody MailDTO mailDTO) throws Exception {

        log.info(this.getClass().getName() + ".sendMail (인증번호 발송) Start");

        String toMail = CmmUtil.nvl(mailDTO.toMail()); // 받는사람

        MailDTO pDTO = MailDTO.builder()
                .toMail(toMail) //받는 사람
                .build();

        MailDTO rDTO = Optional.ofNullable(mailService.sendMail(pDTO))
                .orElseGet(() -> MailDTO.builder().build());


        String msg = "";
        if (rDTO.result() == 1) { //메일발송 성공
            log.info("메일 발송 성공");
        } else { //메일발송 실패
            log.info("메일 발송 실패");
        }

        log.info("리턴 값 : " + rDTO);
        log.info(this.getClass().getName() + ".sendMail (인증번호 발송) End");

        return rDTO;
    }
}