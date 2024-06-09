package poly.graduation_products.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import poly.graduation_products.repository.SocialRepository;
import poly.graduation_products.repository.entity.Provider;
import poly.graduation_products.util.EncryptUtil;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info(this.getClass().getName() + ".loadUser 시작!");

        // DefaultOAuth2UserService를 사용하여 사용자 정보를 로드
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 클라이언트 등록 ID (Google, Naver, Kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 고유 식별자 이름
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // 액세스 토큰
        // String accessToken = userRequest.getAccessToken().getTokenValue();  //이번에는 인증토큰을 사용하지 않음.

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String provider = registrationId.toUpperCase();

        // 사용자 정보 초기화
        String nickname = null;
        String email = null;
        String picture = null;
        Map<String, Object> userAttributes = null;

        // 제공자별로 사용자 정보를 처리
        try {
            // 액세스 토큰 암호화
            // accessToken = EncryptUtil.encHashSHA256(accessToken);  //이번에는 인증토큰을 사용하지 않음.
            switch (provider) {
                case "GOOGLE":
                    nickname = (String) attributes.get("nickname");
                    email = (String) attributes.get("email");
                    picture = (String) attributes.get("picture");
                    userAttributes = attributes;
                    break;
                case "NAVER":
                    // 네이버 로그인에서 반환되는 응답구조는 JSON 형태로 response 라는 키 안에 Map 형식으로 데이터가 구성됨
                    // 내가 받아온 attributes 라는 객체에 response 라는 키 값이 있는지 확인할 필요성이 있음.
                    if (!attributes.containsKey("response") || !(attributes.get("response") instanceof Map)) {
                        log.error("attributes 객체에 response 키가 없음 attributes : " + attributes);
                        throw new IllegalArgumentException("attributes 객체에 response 키가 없음");
                    }
                    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                    nickname = (String) response.get("nickname");
                    email = (String) response.get("email");
                    picture = (String) response.get("profile_image");
                    userAttributes = response;
                    break;
                case "KAKAO":
                    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                    Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                    nickname = (String) profile.get("name");
                    email = (String) kakaoAccount.get("email");
                    picture = (String) profile.get("thumbnail_image_url");
                    userAttributes = attributes;
                    break;
                default:
                    throw new IllegalArgumentException("해당 registrationId는 지원되지 않음  registrationId : " + registrationId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("nickname: " + nickname);
        log.info("email: " + email);
        log.info("provider: " + provider);
        log.info("picture: " + picture);
        // log.info("accessToken: " + accessToken);  //이번에는 인증토큰을 사용하지 않음.

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("nickname", nickname);
        httpSession.setAttribute("email", email);
        httpSession.setAttribute("provider", provider);
        httpSession.setAttribute("picture", picture);
//        httpSession.setAttribute("accessToken", accessToken); //이번에는 인증토큰을 사용하지 않음.

        log.info(this.getClass().getName() + ".loadUser 완료");

        // 다시 SecurityConfiguration 로 이동하여
        // 로그인이 인증이 되었다면 defaultSuccessUrl() 함수가 작동될 것임.
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userAttributes,
                userNameAttributeName);
    }

}