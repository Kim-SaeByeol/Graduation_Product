package poly.graduation_products.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@Slf4j
public class SocialDTO {
    private Map<String, Object> attributes;  // OAuth2 서비스로부터 받은 사용자 정보를 저장하는 맵
    private String attributeKey;  // 사용자의 고유 식별자 키 이름
    private String accessToken;  // 사용자의 액세스 토큰
    private String provider;  // 소셜 플랫폼
    private String email;   //소셜 이메일
}