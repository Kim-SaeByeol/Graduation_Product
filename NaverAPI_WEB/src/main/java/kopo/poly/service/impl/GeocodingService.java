package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.GeocodingDTO;
import kopo.poly.mapper.ICenterMapper;
import kopo.poly.service.ICT_CenterService;
import kopo.poly.service.IGeocodingService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class GeocodingService implements IGeocodingService {

    private final ICenterMapper ICenterMapper;


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
        return requestHeader;
    }

    @Override
    public GeocodingDTO Geocoding(GeocodingDTO gDTO) throws Exception {
        log.info(this.getClass().getName() + ".Geocoding Start!");

        log.info("변환할 address : " + gDTO.getAddress());
        String address = CmmUtil.nvl(gDTO.getAddress());  // 변환할 주소

        gDTO.setAddress(address);

        // 호출할 Geocoding API 정보 설정
        String param = "?query=" + URLEncoder.encode(address, "UTF-8");

        log.info("query : " + param);

        String url = ICT_CenterService.GeocodingApiURL + param;

        log.info("url : " + url);

        // 헤더 정보 설정
        Map<String, String> headers = setNaverInfo();

        // GeocodingAPI 호출하기
        String json = NetworkUtil.get(url, param, headers);

        // json 확인
        log.info("json : " + json);

        // Json 구조를 Map 데이터 구조로 변경
//        CenterDTO rDTO = new ObjectMapper().readValue(json, CenterDTO.class);

        // 수정된 부분
        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);
        List<Map<String, Object>> myList = (List<Map<String, Object>>) rMap.get("addresses");  // 수정된 부분

        if (myList != null && !myList.isEmpty()) {
            String x = (String) myList.get(0).get("x");
            String y = (String) myList.get(0).get("y");

            gDTO.setXAddress(x);
            gDTO.setYAddress(y);

            log.info("값이 잘 들어갔는지 볼까?");
            log.info("x 주소 : " + gDTO.getXAddress());
            log.info("y 주소 : " + gDTO.getYAddress());
            log.info("address : " + gDTO.getAddress());
        } else {
            log.warn("주소 정보가 없습니다.");
        }
        return gDTO;
    }


}
