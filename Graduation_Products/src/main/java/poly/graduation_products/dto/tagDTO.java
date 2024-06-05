package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record tagDTO (
        String tag  //태그 명

        /**
         * 태그 종류
         *
         */
){
}
