package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private LocalAccount localAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SocialAccount> socialAccounts;

    public User(String email, String name, String nickname, Role role) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }
}
