package kopo.poly.Service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICure_CenterService;
import kopo.poly.mapper.ICure_CenterMapper;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class Cure_CenterService implements ICure_CenterService {

    private final ICure_CenterMapper centerMapper;
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
    public CenterDTO Geocoding(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".Geocoding Start!");
        log.info("변환할 address : " + pDTO.getAddress());
        String address = CmmUtil.nvl(pDTO.getAddress());  // 변환할 주소

        // 호출할 Geocoding API 정보 설정
        String param = "?query=" + URLEncoder.encode(address, "UTF-8");

        log.info("query : " + param);

        String url = ICure_CenterService.GeocodingApiURL + param;

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

            pDTO.setX(x);
            pDTO.setY(y);

            log.info("x 주소 : " + x);
            log.info("y 주소 : " + y);
        } else {
            log.warn("주소 정보가 없습니다.");
        }

        return pDTO;
    }

    @Override
    public List<CenterDTO> getCenterList() throws Exception {
        log.info(this.getClass().getName() + ".getCenterList start!");

        List<CenterDTO> resultList = centerMapper.getCure_CenterList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        return centerMapper.getCure_CenterList();
    }

    @Transactional
    @Override
    public void insertCenterInfo(CenterDTO centerDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertCenterInfo Start!");
        centerMapper.insertCure_CenterInfo(centerDTO);

        log.info("잘 들어갔는지 봐볼까?");
        log.info("상태메시지 : " + centerDTO.getStatus());
        log.info("지역명 : " + centerDTO.getRegion());
        log.info("센터명 : " + centerDTO.getCenterName());
        log.info("주소 : " + centerDTO.getAddress());
        log.info("x좌표 : " + centerDTO.getX());
        log.info("y좌표 : " + centerDTO.getY());
        log.info(this.getClass().getName() + ".insertCenterInfo End!");
    }

    @Transactional
    @Override
    public void updateCenterList(CenterDTO centerDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateCenterList start!");
        centerMapper.updateCure_CenterInfo(centerDTO);
        log.info(this.getClass().getName() + ".updateCenterList End!");
    }



    @Transactional
    @Override
    public void deleteCenterList(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteCenterList start!");
        centerMapper.deleteCure_CenterInfo(pDTO);
        log.info(this.getClass().getName() + ".deleteCenterList End!");
    }

    @Override
    public List<CenterDTO> searchCenter_all(String isSido, String centerAddress) throws Exception {
        log.info(this.getClass().getName() + ".searchCenter_all 시작~");
        log.info("결과 들어온 값들 : " + "isSido = " + isSido + " centerAddress = " + centerAddress);

        // 실제 매퍼 호출하여 센터를 조회하고 결과를 반환하는 코드
        return centerMapper.searchCenter_all(isSido, centerAddress);
    }

    @Override
    public List<CenterDTO> searchCenter_sido(String isSido) throws Exception {
        log.info(this.getClass().getName() + ".searchCenter_sido 시작~");
        log.info("도시 값 : " + isSido);

        // 실제 매퍼 호출하여 센터를 조회하고 결과를 반환하는 코드
        return centerMapper.searchCenter_sido(isSido);
    }

    @Override
    public List<CenterDTO> searchCenter_address(String centerAddress) throws Exception {
        log.info(this.getClass().getName() + ".searchCenter_address 시작~");
        log.info("주소 값 : " + centerAddress);

        // 실제 매퍼 호출하여 센터를 조회하고 결과를 반환하는 코드
        return centerMapper.searchCenter_address(centerAddress);
    }

}
