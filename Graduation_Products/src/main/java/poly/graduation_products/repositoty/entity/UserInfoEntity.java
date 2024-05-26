package poly.graduation_products.repositoty.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Cacheable
@Entity
public class UserInfoEntity extends BaseTimeEntity{


    /**
     * GeneratedValue(strategy = GenerationType.IDENTITY)
     * 해당 어노테이션은 해당 식별자가 데이터베이스에 의해 자동으로 생성되고 관리되어야 함을 의미
     * 새로운 엔티티 값이 생성될 때 마다 자동으로 값을 증가시킴
     * 초기 값은 1로, 엔터티가 증가함에 따라 +1 씩 증가할 것임
     */
    @Id
    @Column(name = "USER_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @NonNull
    @Column(name = "USER_ID", length = 16, nullable = false)
    private String userId;

    @NonNull
    @Column(name = "USER_PASSWORD", length = 50, nullable = false)
    private String password;

    @NonNull
    @Column(name = "USER_EMAIL", length = 64, nullable = false)
    private String email;

    @NonNull
    @Column(name = "USER_NAME", length = 20, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "USER_NICKNAME", length = 20, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}