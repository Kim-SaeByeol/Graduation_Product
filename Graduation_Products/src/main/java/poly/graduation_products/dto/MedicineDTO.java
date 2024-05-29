package poly.graduation_products.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
public record MedicineDTO(
        String entpName,       // 업체명
        String itemName,       // 제품명
        String itemSeq,        // 품목기준코드
        String eficacyQesitm,  // 효능
        String useMethodQesitm, // 사용법
        String atpnWarnQesitm, // 사용 전 주의사항
        String atpnQesitm,     // 사용상 주의사항
        String intrcQesitm,    // 상호작용 주의사항
        String seQesitm,       // 부작용
        String depositMethodQesitm, // 보관방법
        String openDe,  // 공개일자
        String updateDe // 수정일자

) {}
