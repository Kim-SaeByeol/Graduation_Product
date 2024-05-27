package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import poly.graduation_products.repository.entity.pk.CommentPK;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT_INFO")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(CommentPK.class)
public class CommentEntity extends BaseTimeEntity{

    @Id
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;      // 게시글 번호

    @Id
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;    // 댓글 번호

    @Column(name = "CONTENTS")
    private String contents;    // 댓글 내용

    @Column(name = "REG_ID")
    private String regId;       // 작성자

    @Column(name = "DEPT", updatable = false)
    private int dept;           //

    @Column(name = "TARGET_SEQ", updatable = false)
    private Long targetSeq;     // 답글번호

    @Column(name = "NICKNAME")
    private String nickname;    //작성자 닉네임

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false)
    private UserInfoEntity userInfo; // 사용자 정보 엔티티와의 연관 관계 정의, 댓글 작성자의 추가 정보를 불러올 때 사용

}
