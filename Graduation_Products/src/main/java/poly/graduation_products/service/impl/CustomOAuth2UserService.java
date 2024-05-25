package poly.graduation_products.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import poly.graduation_products.dto.OAuthAttributes;
import poly.graduation_products.dto.SessionUser;
import poly.graduation_products.repositoty.GoogleRepository;
import poly.graduation_products.repositoty.entity.GoogleLogin;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GoogleRepository googleRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info(this.getClass().getName() + ".loadUser Start!");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //로그인 진행 중인 서비스 구분 (구글, 네이버, 카카오,...)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2User 의 attribute 등을 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        log.info("attributes 의 값은 : " + attributes);


        // 사용자 저장, 세션에 사용자 정보 저장
        GoogleLogin user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));


        log.info(this.getClass().getName() + ".loadUser End");

        return new DefaultOAuth2User(
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private GoogleLogin saveOrUpdate(OAuthAttributes attributes) {

        log.info(this.getClass().getName() + ".saveOrUpdate Start");

        GoogleLogin user = googleRepository.findByEmail(attributes.getEmail())
                // 가입 O -> 정보 업데이트
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                // 가입 X -> User 엔티티 생성
                .orElse(attributes.toEntity());


        log.info("user 의 값은  : " + user );

        log.info(this.getClass().getName() + ".saveOrUpdate End");

        return googleRepository.save(user);
    }
}