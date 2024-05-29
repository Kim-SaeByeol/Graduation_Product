package poly.graduation_products.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import poly.graduation_products.repository.entity.pk.CommentPK;

import java.time.LocalDateTime;

/**
 * 댓글 엔티티 클래스입니다.
 * 댓글 정보와 관련된 필드를 정의하며, JPA를 통해 데이터베이스와 매핑됩니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT")
@DynamicInsert
@DynamicUpdate
@Builder
@Entity
@IdClass(CommentPK.class)
public class CommentEntity extends BaseTimeEntity{

    /**
     * 게시물 ID (복합키의 일부).
     */
    @Id
    @Column(name = "BOARD_SEQ")
    private Long boardSeq;

    /**
     * 댓글 순서 ID (복합키의 일부).
     */
    @Id
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    /**
     * 댓글 내용.
     */
    @Column(name = "CONTENTS")
    private String contents;

    /**
     * 댓글 작성자 ID.
     */
    @Column(name = "REG_ID")
    private String regId;

    /**
     * 댓글 깊이 (0: 일반 댓글, 1: 대댓글).
     */
    @Column(name = "DEPT", updatable = false)
    private int dept;

    /**
     * 대상 댓글의 ID (대댓글의 경우).
     */
    @Column(name = "TARGET_SEQ", updatable = false)
    private Long targetSeq;

    /**
     * 댓글 작성자 닉네임.
     */
    @Column(name = "NICKNAME")
    private String nickname;

    /**
     * 댓글 작성자 정보와의 다대일 연관 관계.
     * 댓글 작성자의 ID와 매핑됩니다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_ID", insertable = false, updatable = false)
    private UserInfoEntity userInfo;
}
