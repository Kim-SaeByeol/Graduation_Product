package kopo.poly.Service;

import kopo.poly.DTO.GeocodingDTO;

public interface IGeocodingService {

    //GeocodingAPI 호출링크 (주소 -> 위도, 경도 변환 )
    String GeocodingApiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";

    // GeocodingAPI를 호출하여 입력된 주소를 변환하기
    GeocodingDTO Geocoding(GeocodingDTO pDTO) throws Exception;
}