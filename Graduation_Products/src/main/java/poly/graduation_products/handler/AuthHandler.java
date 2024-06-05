package poly.graduation_products.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import poly.graduation_products.repository.SocialRepository;
import poly.graduation_products.repository.entity.SocialInfoEntity;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthHandler implements AuthenticationSuccessHandler {

    private final SocialRepository socialRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("소셜 핸들러 작동");
        // 세션에서 email과 provider 가져오기
        String email = (String)request.getSession().getAttribute("email");
        String provider = (String)request.getSession().getAttribute("provider");


        // DB에 해당 회원이 있는지 확인하기
        Optional<SocialInfoEntity> rEntity = socialRepository.findByEmailAndProvider(email, provider);

        // 회원이 존재하면 /index/index로 리다이렉트
        if (rEntity.isPresent()) {
            response.sendRedirect("/index/index");
            log.info("소셜 핸들러 종료 (메인으로)");

        } else {
            // 회원이 없으면 /social/socialRegForm로 리다이렉트
            response.sendRedirect("/social/socialRegForm");
            log.info("소셜 핸들러 종료 (회원가입으로)");
        }
    }
}
