package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SOCIAL_INFO")
public class SocialInfoEntity extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOCIAL_SEQ")
    private Long socialSEQ;      //pk 순번

    @NonNull
    @Column(name = "SOCIAL_EMAIL", nullable = false)
    private String socialEmail;       // 플랫폼 이메일

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", nullable = false)
    private Provider provider;      // 플랫폼 명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private UserInfoEntity userInfo;    // 조인
}
