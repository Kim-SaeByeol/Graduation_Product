package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "social_accounts")
public class SocialAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider authProvider;

    @Column(nullable = false)
    private String accessToken;

    public SocialAccount(User user, Provider authProvider, String accessToken) {
        this.user = user;
        this.authProvider = authProvider;
        this.accessToken = accessToken;
    }
}
