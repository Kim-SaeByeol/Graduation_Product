package poly.graduation_products.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import poly.graduation_products.dto.MailDTO;
import poly.graduation_products.service.IMailService;
import poly.graduation_products.util.CmmUtil;
import poly.graduation_products.util.RandomUtil;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {

    private final JavaMailSender mailSender;

    /*
     개인정보 보호를 위해 해당 서비스에 발신자를 보내는 것이 아닌
     application.properties 에 작성하여
     해당 내용을 호출하는 것으로 작성
     */
    @Value("${spring.mail.username}")
    private String from;    //보내는 이메일 주소

    @Override
    public MailDTO sendMail(MailDTO pDTO) {

        log.info(this.getClass().getName() + ".SendMail(메일전송) start!");

        MailDTO rDTO;

        //영어 소문자, 대문자와 숫자로 이루어진 6자리 난수코드 생성
        String OTP = RandomUtil.generateRandomCode(6);  // 인증번호
        log.info("OTP : " + OTP);

        String toMail = CmmUtil.nvl(pDTO.toMail()); // 받는 사람
        String title = "[케어트랙] 이메일 인증번호 발송";    // 제목
        String text = "케어트랙 이메일 인증번호 : [" + OTP+"]\n 해당 인증번호를 입력해주세요."; // 내용

        log.info("이메일 제목 : " + title);
        log.info("이메일 내용 : " + text);

        /**
         * res 는 결과를 저장할 변수
         * 0 : 실패
         * 1 : 성공
         * 2 : 이미 사용자가 존재 (실패)
         * 3 : 에러
         */
        int res;

        //DTO객체가 메모리에 올라가지 않아 Null이 발생할 수 있기 때문에 에러방지차원으로 if문 사용함
        if (pDTO == null) {
            log.error("pDTO is null");
            res = 3;
            rDTO = MailDTO.builder()
                    .result(res)
                    .build();
            return rDTO;
        }

        MimeMessage m = mailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(m,"UTF-8");

        try {
            h.setTo(toMail);
            h.setFrom(from);
            h.setSubject(title);
            h.setText(text);
            mailSender.send(m);
            log.info(this.getClass().getName() + ".sendMail - 메일전송 성공");
            res = 1; // 성공적으로 메일 전송
        } catch (MessagingException e) {
            /**
             * MessagingException 은 JavaMail API 에서 발생하는 예외를 처리함
             * 그 중에서도 메일전송과정에 대한 오류를 담당하고 있음.
             * 1. 연결문제 (메일 서버와 연결이 되는지)
             * 2. 인증문제 (보내는 메일의 아이디와 비밀번호가 맞는지)
             * 3. 허가거부 (상대방이 보내는 메일을 차단,거부 하여 못 보낼 경우)
             * 4. 프로토콜 (메일전송 프로토콜 오류)
             * 5. 기타서버 (서버, 설정 등 서버가 일시적으로 응답하지 않는 경우)
             */
            res = 0; // 메일 전송 실패
            log.error(this.getClass().getName() + ".sendMail - 메일전송 실패 (에러 이유): ", e);
        } catch (Exception e) {
            res = 3; // 그 외 기타 예외
            log.error(this.getClass().getName() + ".sendMail - 메일전송 오류: ", e);
        }

        rDTO = MailDTO.builder()
                .result(res)
                .authNumber(OTP)
                .build();

        log.info(this.getClass().getName() + ".SendMail(메일전송) end!");
        return rDTO;
    }
}