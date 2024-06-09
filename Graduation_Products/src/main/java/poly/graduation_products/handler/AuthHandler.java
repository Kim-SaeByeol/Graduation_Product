package poly.graduation_products.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import poly.graduation_products.repository.SocialRepository;
import poly.graduation_products.repository.entity.Provider;
import poly.graduation_products.repository.entity.SocialInfoEntity;
import poly.graduation_products.repository.entity.UserInfoEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthHandler implements AuthenticationSuccessHandler {

    private final HttpSession httpSession;

    private final SocialRepository socialRepository;

    HttpSession session;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("소셜 핸들러 작동");

        // 세션에서 값 가져오기
        String email = (String)httpSession.getAttribute("email");
        String provider = (String)httpSession.getAttribute("provider");
        String picture = (String)httpSession.getAttribute("picture");

        log.info(this.getClass().getName() + " email : " + email);
        log.info(this.getClass().getName() + " provider : " + provider);
        log.info(this.getClass().getName() + " picture : " + picture);

        // provider 문자열을 Provider enum으로 변환
        String providerStr = provider;
        Provider provider1 = Provider.valueOf(providerStr.toUpperCase());

        // DB에 해당 회원이 있는지 확인하기
        Optional<SocialInfoEntity> rEntity = socialRepository.findBySocialEmailAndProvider(email, provider1);

        log.info(this.getClass().getName() + " rEntity : " + rEntity);

        // 회원이 존재하면 /index/index로 리다이렉트
        if (rEntity.isPresent()) {
            UserInfoEntity userInfo = rEntity.get().getUserInfo();
            String nickname = userInfo.getNickname();

            httpSession.setAttribute("SS_USER_NAME", nickname);

            // 세션에 저장된 값 삭제
            httpSession.removeAttribute("email");
            httpSession.removeAttribute("provider");
            httpSession.removeAttribute("picture");
            // httpSession.removeAttribute("accessToken"); // 이번에는 인증토큰을 사용하지 않음.

            log.info("소셜 핸들러 종료 (메인으로)");
            response.sendRedirect("/index/index");

        } else {
            // 리다이렉션을 위한 세션 저장
            httpSession.setAttribute("Check", "Check");

            // 회원이 없으면 /social/socialRegForm로 리다이렉트
            response.sendRedirect("/social/socialRegForm");

            log.info("소셜 핸들러 종료 (회원가입으로)");
        }
    }
}
