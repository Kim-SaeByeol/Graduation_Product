package poly.graduation_products.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import poly.graduation_products.dto.MailDTO;
import poly.graduation_products.dto.MsgDTO;
import poly.graduation_products.service.IMailService;
import poly.graduation_products.util.CmmUtil;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/mail")
@Controller
public class MailController {

    private final IMailService mailService; // 메일 발송을 위한 서비스 객체를 사용하기

    /**
     * 메일 발송하기 폼
     */

    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {

        // 로그 찍기
        log.info(this.getClass().getName() + "mailForm Start!");

        return "/mail/mailForm";
    }

    /**
     * 메일 발송하기
     */

    @ResponseBody
    /* Ajax -> Json 형식 변경 */
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".sendMail Start!");

        String msg = ""; // 발송 결과 메세지

        // 웹 URL로부터 전달 받는 값들
        String toMail = CmmUtil.nvl(request.getParameter("toMail")); // 받는 사람
        String title = CmmUtil.nvl(request.getParameter("title")); // 제목
        String text = CmmUtil.nvl(request.getParameter("text")); // 내용

        /*
         * 들어온 값 확인
         */

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("text " + text);

        MailDTO pDTO = MailDTO.builder()
                .toMail(toMail)
                .title(title)
                .text(text)
                .build();


        //메일 발송하기
        int res = mailService.sendMail(pDTO);

        if(res == 1) { // 메일 발송 성공
            msg = "메일 발송하였습니다.";
        } else { //메일발송 실패
            msg = "메일 발송 실패하였습니다.";
        }

        log.info(msg);

        MsgDTO dto = MsgDTO.builder()
                .msg(msg)
                .build();

        log.info(this.getClass().getName() + ".sendMail End!");

        return dto;
    }

}
