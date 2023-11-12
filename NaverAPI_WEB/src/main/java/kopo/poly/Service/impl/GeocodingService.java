package kopo.poly.Service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.DTO.GeocodingDTO;
import kopo.poly.Service.IGeocodingService;
import kopo.poly.utill.CmmUtil;
import kopo.poly.utill.NetworkUtil;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class GeocodingService implements IGeocodingService {

    /**
     * Naver API 사용을 위한 접속 정보 설정
     */
    @Value("${naver.geocode.clientId}")
    private String ClientID;

    @Value("${naver.geocode.clientSecret}")
    private String ClientSecret;

    private Map<String, String> setNaverInfo() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-NCP-APIGW-API-KEY-ID", ClientID);
        requestHeader.put("X-NCP-APIGW-API-KEY", ClientSecret);

        log.info("ClientID : " + ClientID);
        log.info("ClientSecret : " + ClientSecret);

        return requestHeader;
    }


    @Override
    public GeocodingDTO Geocoding(GeocodingDTO pDTO) throws Exception {

      log.info(this.getClass().getName() + ".Geocoding Start!");

      String address = CmmUtil.nvl(pDTO.getAddress());  // 변환할 주소

      //호출할 Geocoding API 정보 설정
      String param = "query=" + URLEncoder.encode(address, "UTF-8");

      //GeocodingAPI 호출하기
        String json = NetworkUtil.get(IGeocodingService.GeocodingApiURL, this.setNaverInfo(), param);
        /**
         * 호출 예시)
         * json =
         * https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocodem,
         * X-NCP-APIGW-API-KEY-ID : "ClientID",
         * X-NCP-APIGW-API-KEY : "ClientSecret",
         * "query= {변활할 주소}"
         */

        //json 확인
        log.info("json : "+ json);

        //Json 구조를 Map 데이터 구조로 변경
        GeocodingDTO rDTO = new ObjectMapper().readValue(json, GeocodingDTO.class);

        //address 저장
        rDTO.setAddress(address);


      log.info(this.getClass().getName() + ".Geocoding End!");

        return rDTO;
    }
}
