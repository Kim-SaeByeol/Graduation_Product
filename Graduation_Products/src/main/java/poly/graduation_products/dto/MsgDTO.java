package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record MsgDTO(
        int result,     // 성공하면 1 반환, 실패하면 0
        String msg      // 메세지로 넣을 변수
) {
}
