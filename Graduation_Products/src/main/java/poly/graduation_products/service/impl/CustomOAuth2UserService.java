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
import poly.graduation_products.repository.SocialLoginRepository;
import poly.graduation_products.repository.entity.SocialLoginEntity;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final SocialLoginRepository socialLoginRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info(this.getClass().getName() + ".loadUser 시작!");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        String accessToken = userRequest.getAccessToken().getTokenValue();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes(), accessToken);
        log.info("attributes 값: " + attributes);

        SocialLoginEntity user = saveOrUpdate(attributes);
        updateSession(user);

        log.info(this.getClass().getName() + ".loadUser 완료");

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private SocialLoginEntity saveOrUpdate(OAuthAttributes attributes) {
        log.info(this.getClass().getName() + ".saveOrUpdate Start");

        SocialLoginEntity user = socialLoginRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getNickname(), attributes.getPicture(), attributes.getAccessToken()))
                .orElse(attributes.toEntity());

        log.info("Updated or saved user: " + user);
        log.info(this.getClass().getName() + ".saveOrUpdate End");

        return socialLoginRepository.save(user);
    }

    private void updateSession(SocialLoginEntity user) {
        httpSession.setAttribute("SS_USER_ID", user.getAccessToken());
        String displayName = user.getNickname() != null ? user.getNickname() : user.getName();
        httpSession.setAttribute("SS_USER_NAME", displayName);

        log.info("SS_USER_ID : " + user.getAccessToken());
        log.info("SS_USER_NAME : " + displayName);
    }
}
