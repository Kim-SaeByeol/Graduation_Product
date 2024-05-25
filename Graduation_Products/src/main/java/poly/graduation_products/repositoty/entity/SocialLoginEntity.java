package poly.graduation_products.repositoty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SOCIAL_LOGIN_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Cacheable
@Entity
public class SocialLoginEntity {
    @Id
    @Column(name = "ID", length = 20, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * UserInfoEntity와의 다대일 관계를 설정
     * USER_SEQ 를 외래키로 사용하여 서로 조인이 가능하도록 함
     * LAZY 로딩을 통해 SocialLoginEntity 를 조회하여도 UserInfoEntity를 로드하지 않게하여
     * 불 필요한 DB사용을 줄임
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private UserInfoEntity user;

    @Column(name = "PROVIDER", length = 20, nullable = false)
    private String provider; // 소셜 서비스 제공자 이름 (예: google, naver, kakao)

    @Column(name = "SOCIAL_NAME", length = 20, nullable = false)
    private String name; //  이름

    @Column(name = "SOCIAL_NICK", length = 20, nullable = false)
    private String nick; //  별명

    @Column(name = "ACCESS_TOKEN", length = 512)
    private String accessToken; // 액세스 토큰

    @Column(name = "REFRESH_TOKEN", length = 512)
    private String refreshToken; // 리프레시 토큰

    @Column(name = "TOKEN_EXPIRATION")
    private LocalDateTime tokenExpiration; // 토큰 만료 시간

}
