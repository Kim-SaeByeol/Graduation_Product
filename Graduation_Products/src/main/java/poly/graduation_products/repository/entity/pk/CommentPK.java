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

    private Long boardSeq;

    private Long commentSeq;
}
