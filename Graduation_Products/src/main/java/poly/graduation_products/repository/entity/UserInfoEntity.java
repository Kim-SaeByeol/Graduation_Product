package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfoEntity extends BaseTimeEntity implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "PASSWORD")
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @NonNull
    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;


}
