package kopo.poly.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)   //값이 비어있는 경우 해당 필드를 JSON으로 변환하지 않음.
public class GeocodingDTO {

    private String address;     // 변환 시킬 주소
    private String xAddress;    // x 좌표
    private String yAddress;    // y 좌표
}
