package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record MailDTO (

    String toMail, // 받는 사람
    String title, // 보내는 메일 제목
    String text,  // 보내는 메일 내용
    String authNumber,  // 이메일 인증번호

    int result  // 상태 값

){
}
