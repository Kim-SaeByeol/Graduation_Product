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
import poly.graduation_products.service.IMailService;
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
    public int sendMail(@RequestBody MailDTO mailDTO) throws Exception {

        log.info(this.getClass().getName() + ".sendMail (인증번호 발송) Start");

        String toMail = CmmUtil.nvl(mailDTO.toMail()); // 받는사람

        //영어 소문자, 대문자와 숫자로 이루어진 6자리 난수코드 생성
        String OTP = RandomUtil.generateRandomCode(6);  // 인증번호
        log.info("OTP : " + OTP);

        String title = "[케어트랙] 이메일 인증번호 발송";
        String text = "케어트랙 이메일 인증번호 : [" + OTP+"]\n 해당 인증번호를 입력해주세요.";

        log.info("toMail(삭제필요) : " + toMail);
        log.info("이메일 제목 : " + title);
        log.info("이메일 내용 : " + text);

        MailDTO pDTO = MailDTO.builder()
                .toMail(toMail) //받는 사람
                .title(title)   // 제목
                .text(text)     // 내용
                .build();

        int res = mailService.sendMail(pDTO);


        String msg = "";
        if (res == 1) { //메일발송 성공
            log.info("메일 발송 성공");
        } else { //메일발송 실패
            log.info("메일 발송 실패");
        }
        log.info(this.getClass().getName() + ".sendMail (인증번호 발송) End");

        return res;
    }
}