package poly.graduation_products.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record UserInfoDTO (

        String userId,  //아이디
        String userPassword,    //비밀번호
        String email,   //이메일
        String userNickname,    //별명
        String role,        // 권한
        String profilePath, // 프로필 사진 경로


        //가상의 컬럼
        String existsIdYn,    //아이디 중복 방지
        String existsEmailYn,    //이메일 중복 방지
        String existsNickYn,    //별명 중복 방지
        String authNumber, //인증번호
        int mailNumber

){
}
