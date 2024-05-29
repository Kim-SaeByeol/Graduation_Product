package poly.graduation_products.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import poly.graduation_products.repository.entity.Role;
import poly.graduation_products.service.impl.CustomOAuth2UserService;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration{

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/**", "/index/**", "/user/**", "/board/**",
                                "/customerSvc/**", "/mail/**", "/medicine/**",
                                "/gallery/**", "/css/**", "/images/**", "/js/**",
                                "/icon/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutSuccessUrl("/index/index")) //로그아웃 성공하면 해당 주소로 이동
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/user/login") // 사용자 정의 로그인 페이지 URL
                        .defaultSuccessUrl("/index/index") // Login 성공 후 이동할 URL
                        .failureUrl("/user/login?error=true") // Login 실패 시 이동할 URL
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)   //로그인 성공 이후 사용자 정보를 가져오기 위한 코드
                        )
                );
        return http.build();
    }

}