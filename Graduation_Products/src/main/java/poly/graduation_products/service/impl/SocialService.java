package poly.graduation_products.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.graduation_products.repository.SocialRepository;
import poly.graduation_products.repository.UserInfoRepository;
import poly.graduation_products.repository.entity.Provider;
import poly.graduation_products.repository.entity.Role;
import poly.graduation_products.repository.entity.SocialInfoEntity;
import poly.graduation_products.repository.entity.UserInfoEntity;
import poly.graduation_products.service.ISocialService;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialService implements ISocialService {

    private final SocialRepository socialRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public void insertSocialInfo(String nickname, String userEmail, String profile, String socialEmail, String provider) throws Exception {

        log.info(this.getClass().getName() + "소셜 회원가입 실행");

        // provider 문자열을 Provider enum으로 변환
        String providerStr = provider;
        Provider provider1 = Provider.valueOf(providerStr.toUpperCase()); // 변수 이름 변경

        UserInfoEntity userInfoEntity = UserInfoEntity.builder()
                .nickname(nickname)
                .email(userEmail)
                .profilePath(profile)
                .role(Role.USER)
                .build();

        // userInfoEntity 에 정보를 저장하여 Social 테이블에서 처리할 수 있게 함.
        userInfoEntity = userInfoRepository.save(userInfoEntity);

        SocialInfoEntity socialInfoEntity = SocialInfoEntity.builder()
                .socialEmail(socialEmail)
                .provider(provider1)
                .userInfo(userInfoEntity)
                .build();

        // 소셜 테이블에 저장
        socialRepository.save(socialInfoEntity);

        log.info(this.getClass().getName() + "소셜 회원가입 종료");
    }

    @Override
    public int socialLink(String socialEmail, String provider) throws Exception {
        return 0;
    }
}
