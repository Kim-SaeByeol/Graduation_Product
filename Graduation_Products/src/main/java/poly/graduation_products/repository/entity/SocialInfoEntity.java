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
    @Column(name = "SOCIAL_ID")
    private Long socialId;

    @NonNull
    @Column(name = "SOCIAL_EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "ACCESS_TOKEN", nullable = false)
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserInfoEntity userInfo;
}
