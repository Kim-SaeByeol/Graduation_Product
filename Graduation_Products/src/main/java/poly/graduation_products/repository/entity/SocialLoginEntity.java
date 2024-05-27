package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "SocialUserInfo")
@Entity
public class SocialLoginEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Column
    private String nickname; // 추가된 필드

    @Column
    private String accessToken; // 추가된 필드

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Builder
    public SocialLoginEntity(String name, String email, String picture, String nickname, String accessToken, Role role, Provider provider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.role = role;
        this.provider = provider;

    }
    // 업데이트 메서드 수정
    public SocialLoginEntity update(String name, String nickname, String picture, String accessToken) {
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
        this.accessToken = accessToken;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
