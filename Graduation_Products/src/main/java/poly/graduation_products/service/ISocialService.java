package poly.graduation_products.service;

public interface ISocialService {

    //회원가입
    void insertSocialInfo(
                       final String socialEmail, // 소셜 이메일
                       final String provider,    // 플랫폼 명
                       final String userEmail,  // 대표 이메일
                       final String nickName,   // 유저 별명
                       final String profilePath // 프로필 주소
    ) throws Exception;

    // 소셜 연동 확인 회원정보를 통해 조회
    int socialLink(final String socialEmail, final String provider) throws Exception;

}
