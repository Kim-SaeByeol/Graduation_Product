package poly.graduation_products.repository.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tag {
    HYPERTENSION("Hypertension", "고혈압"),
    DIABETES("Diabetes Mellitus", "당뇨병"),
    HYPERLIPIDEMIA("Hyperlipidemia", "고지혈증"),
    ANGINA("Angina", "협심증"),
    MYOCARDIAL_INFARCTION("Myocardial Infarction", "심근경색"),
    HEART_FAILURE("Heart Failure", "심부전"),
    STROKE("Stroke", "뇌졸중"),
    CEREBRAL_INFARCTION("Cerebral Infarction", "뇌경색"),
    COPD("Chronic Obstructive Pulmonary Disease", "만성 폐쇄성 폐질환"),
    ASTHMA("Asthma", "천식"),
    CHRONIC_KIDNEY_DISEASE("Chronic Kidney Disease", "만성 신장 질환"),
    CHRONIC_HEPATITIS("Chronic Hepatitis", "만성 간염"),
    LIVER_CIRRHOSIS("Liver Cirrhosis", "간경변"),
    CANCER("Cancer", "암");

    private final String englishName;
    private final String comment;
}
