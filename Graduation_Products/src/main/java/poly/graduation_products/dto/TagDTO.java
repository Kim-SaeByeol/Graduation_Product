package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record TagDTO(
        List<String> tags

){
}
