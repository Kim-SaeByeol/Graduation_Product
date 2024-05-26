package poly.graduation_products.dto;

import lombok.Getter;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import poly.graduation_products.repositoty.entity.Provider;
import poly.graduation_products.repositoty.entity.Role;
import poly.graduation_products.repositoty.entity.SocialLoginEntity;

import java.util.Map;

@Slf4j
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;  // OAuth2 서비스로부터 받은 사용자 정보를 저장하는 맵
    private String nameAttributeKey;  // 사용자의 고유 식별자 키 이름
    private String name;  // 사용자의 이름
    private String nickname;  // 사용자의 별명 (추가)
    private String email;  // 사용자의 이메일 주소
    private String picture;  // 사용자의 프로필 이미지 URL
    private String accessToken;  // 사용자의 액세스 토큰 (추가)
    private String provider;  // 소셜 플랫폼

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String nickname, String email, String picture, String accessToken, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.accessToken = accessToken;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        switch (registrationId.toUpperCase()) {
            case "GOOGLE":
                return ofGoogle(userNameAttributeName, attributes, accessToken);
            case "NAVER":
                return ofNaver("id", attributes, accessToken);
            case "KAKAO":
                return ofKakao(userNameAttributeName, attributes, accessToken);
            default:
                throw new IllegalArgumentException("Unsupported registrationId: " + registrationId);
        }
    }


    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .nickname((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .accessToken(accessToken)
                .provider("GOOGLE")  // 여기에 Provider를 지정
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        // 'response' 키의 존재 유무를 확인합니다.
        if (!attributes.containsKey("response") || !(attributes.get("response") instanceof Map)) {
            log.error("Missing 'response' attribute in provided attributes: " + attributes);
            throw new IllegalArgumentException("Missing attribute 'response' in attributes");
        }

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        log.info("Attributes received: " + attributes);
        log.info("response : " + response);

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .nickname((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .accessToken(accessToken)
                .provider("NAVER")
                .build();
    }





    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes, String accessToken) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuthAttributes.builder()
                .name((String) profile.get("nickname"))
                .nickname((String) profile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) profile.get("thumbnail_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .accessToken(accessToken)
                .provider("KAKAO")  // 여기에 Provider를 지정
                .build();
    }


    // DTO 정보를 Entity에 넘김
    public SocialLoginEntity toEntity() {
        return SocialLoginEntity.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .nickname(nickname)
                .accessToken(accessToken)
                .role(Role.GUEST) // 초기 롤 설정, 변경 필요 시 조정
                .provider(Provider.valueOf(provider))
                .build();
    }

}