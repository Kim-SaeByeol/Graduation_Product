package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CenterDTO {

    private int seq;               // 순번
    private String region;        // 지역
    private String centerName;   // 센터명
    private String address;     // 주소
    private String phone;      // 담당 연락처

    //
    private String work;    //주요 업무
    private String explan;  //설명

    //Geocoding
    private String x;    // x 좌표
    private String y;    // y 좌표

    private String status;           // 상태메시지
    private String errorMessage;    //오류 메시지

}