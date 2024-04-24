package poly.graduation_products.service;

import jakarta.mail.MessagingException;
import poly.graduation_products.dto.MailDTO;

public interface IMailService {

    //메일 발송
    MailDTO sendMail(MailDTO pDTO) throws MessagingException;
}