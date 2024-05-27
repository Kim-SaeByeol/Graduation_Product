package poly.graduation_products.repository.entity.pk;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommentPK implements Serializable {

    /**
     * 게시글과 댓글의 복합키 사용을 위한 클래스
     */

    private Long boardSeq;  //게시글 번호

    private Long commentSeq;    // 댓글 번호
}