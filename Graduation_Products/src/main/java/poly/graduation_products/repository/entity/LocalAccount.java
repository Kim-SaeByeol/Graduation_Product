package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "local_accounts")
public class LocalAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public LocalAccount(User user, String username, String password) {
        this.user = user;
        this.username = username;
        this.password = password;
    }
}
