package poly.graduation_products.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record UserInfoDTO (

        String userSeq, //회원번호
        String userId,  //아이디
        String userPassword,    //비밀번호
        String userEmail,   //이메일
        String userName,    //이름
        String userNickname,    //별명
        String userSince,   //가입일자
        String userRoles,    //권한

        //가상의 컬럼
        String existsIdYn,    //아이디 중복 방지
        String existsEmailYn,    //이메일 중복 방지
        String existsNickYn,    //별명 중복 방지
        int authNumber  // 이메일 인증번호

){
}
