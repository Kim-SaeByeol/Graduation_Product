package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.io.Serializable;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값이 아닌 필드만 JSON에 포함
public record SocialLoginDTO(
        String nickname,
        String email,
        String picture,
        String gender,
        String age
)implements Serializable {
}
