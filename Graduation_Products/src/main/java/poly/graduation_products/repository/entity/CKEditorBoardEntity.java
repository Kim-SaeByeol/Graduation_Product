package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOARD_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Getter
@Setter
@Cacheable
@Entity
public class CKEditorBoardEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;      //게시글 번호

    @Column(name = "REG_ID", length = 500, nullable = false, updatable = false)
    private String regId;       //게시글 등록자 UserID

    @Column(name = "TITLE", length = 25, nullable = false)
    private String title;       //게시글 제목

    @Column(name = "CATEGORY", length = 10, nullable = false)
    private String category;    //카테고리 (자유, 조언, 건강)

    @Column(name = "CONTENTS", length = 5000, nullable = false)
    private String contents;    //게시글 내용

    @Column(name = "READ_CNT", nullable = false)
    private Long readCnt;       // 조회수

    @Column(name = "COMMENT_CNT")
    private Long commentCnt;    // 댓글 수

    @Column(name = "NICKNAME")
    private String nickname;    //게시글 등록자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false)
    private UserInfoEntity userInfo;
}
