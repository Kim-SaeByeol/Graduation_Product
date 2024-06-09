package poly.graduation_products.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tag {
    HealthInformation("건강정보"),
    NewsInformation("뉴스정보"),
    Medicines("의약품"),
    MedicineManagement("의약품관리");

    private final String title;
}
