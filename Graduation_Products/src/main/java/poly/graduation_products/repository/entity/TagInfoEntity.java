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
@Table(name = "TAG_INFO")
public class TagInfoEntity extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAG_SEQ")
    private Long tagSEQ;     // 태그 순번

    @Enumerated(EnumType.STRING)
    @Column(name = "TAG_NAME")
    private Tag tagName;    // 태그 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ", nullable = false)
    private UserInfoEntity userInfo;    // 조인
}
