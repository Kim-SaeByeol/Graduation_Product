package kopo.poly.service;

import kopo.poly.dto.CenterDTO;

import java.util.List;

public interface ICT_CenterService {

    //GeocodingAPI 호출링크 (주소 -> 위도, 경도 변환 )
    String GeocodingApiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";


    // GeocodingAPI를 호출하여 입력된 주소를 변환하기
    CenterDTO Geocoding(CenterDTO pDTO) throws Exception;

    //조회
    List<CenterDTO> getCenterList() throws Exception;


    //추가
    void insertCenterInfo(CenterDTO pDTO) throws Exception;

    //수정
    void updateCenterList(CenterDTO centerDTO) throws Exception;

    //삭제
    void deleteCenterList(CenterDTO pDTO) throws Exception;

    //검색기능 (도시 와 주소를 함께 입력하였을 때)
    List<CenterDTO> searchCenter_all(String isSido, String centerAddress) throws Exception;

    //검색기능 (도시만 입력하였을 떄)
    List<CenterDTO> searchCenter_sido(String isSido) throws Exception;

    //검색기능 (주소만 입력하였을 떄)
    List<CenterDTO> searchCenter_address(String centerAddress) throws Exception;

}
