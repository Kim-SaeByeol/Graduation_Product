package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfoEntity extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSEQ;      //pk 순번

    @Column(name = "USER_ID")
    private String userId;      // 유저 아이디

    @Column(name = "PASSWORD")
    private String password;    // 비밀번호

    @NonNull
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;       // 대표 이메일

    @NonNull
    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;    // 별명

    @NonNull
    @Column(name = "PROFILE_PATH", nullable = false, unique = true)
    private String profilePath;    // 프로필

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;          // 권한

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SocialInfoEntity> socialInfos; // 소셜 정보 리스트

    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagInfoEntity> tagInfos; // 태그 정보 리스트

}
