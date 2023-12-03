package kopo.poly.mapper;

import kopo.poly.dto.CenterDTO;

public interface IGeocodingMapper {

    //Geocoding 값 저장하기
    void insertGeocoding(CenterDTO pDTO) throws Exception;

    //Geocoding 값 수정하기
    void updateGeocoding(CenterDTO pDTO) throws Exception;
}
