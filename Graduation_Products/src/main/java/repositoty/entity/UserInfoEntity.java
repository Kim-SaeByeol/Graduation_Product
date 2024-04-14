package repositoty.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Cacheable
@Entity
public class UserInfoEntity {

    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @NonNull
    @Column(name = "USER_ID", length = 10, nullable = false)
    private String userId;

    @NonNull
    @Column(name = "USER_PASSWORD", length = 50, nullable = false)
    private String pwd;

    @NonNull
    @Column(name = "USER_EMAIL", length = 64, nullable = false)
    private String email;

    @NonNull
    @Column(name = "USER_NAME", length = 20, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "USER_NICKNAME", length = 20, nullable = false)
    private String nickname;

    @NonNull
    @Column(name = "USER_SINCE", nullable = false)
    private String userSince;

    @NonNull
    @Column(name = "USER_ROLES", length = 64, nullable = false)
    private String roles;
}
