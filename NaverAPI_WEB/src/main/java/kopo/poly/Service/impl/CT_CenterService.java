package kopo.poly.Service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.DTO.CenterDTO;
import kopo.poly.Service.ICenterService;
import kopo.poly.Service.IGeocodingService;
import kopo.poly.mapper.ICT_CenterMapper;
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
public class CT_CenterService implements ICenterService {

    private final ICT_CenterMapper centerMapper;

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

        String url = ICenterService.GeocodingApiURL + param;

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

        List<CenterDTO> resultList = centerMapper.getCT_CenterList();
        if (resultList == null) {
            log.info("resultList is null!");
        } else if (resultList.isEmpty()) {
            log.info("resultList is empty!");
        } else {
            log.info("resultList size: " + resultList.size());
        }

        return centerMapper.getCT_CenterList();
    }

    @Transactional
    @Override
    public void insertCenterInfo(CenterDTO centerDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertCenterInfo Start!");
        centerMapper.insertCT_CenterInfo(centerDTO);

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
        centerMapper.updateCT_CenterInfo(centerDTO);
        log.info(this.getClass().getName() + ".updateCenterList End!");
    }



    @Transactional
    @Override
    public void deleteCenterList(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteCenterList start!");
        centerMapper.deleteCT_CenterInfo(pDTO);
        log.info(this.getClass().getName() + ".deleteCenterList End!");
    }


    /**
     * 동일한 검색기능을 하나의 코드로 표현하여
     *  코드의 중복성을 없앰.
     */

    private List<CenterDTO> searchCenterCommon(String isSido, String centerAddress, String methodName) throws Exception {
        log.info(this.getClass().getName() + "." + methodName + " Start!");

        try {
            // MyBatis를 통해 DB에서 검색을 수행하는 로직을 작성
            // 예를 들어, CenterMapper와 연동하여 해당 정보를 가져올 수 있음
            return (List<CenterDTO>) centerMapper.getClass().getMethod(methodName, String.class, String.class)
                    .invoke(centerMapper, isSido, centerAddress);
        } catch (Exception e) {
            log.error("검색 중 오류 발생: " + e.getMessage());
            throw e;
        } finally {
            log.info(this.getClass().getName() + "." + methodName + " End!");
        }
    }



    @Override
    public List<CenterDTO> searchCenter_all(String isSido, String centerAddress) throws Exception {
        return searchCenterCommon(isSido, centerAddress, "searchCenter_all");
    }

    @Override
    public List<CenterDTO> searchCenter_sido(String isSido) throws Exception {
        return searchCenterCommon(isSido, null, "searchCenter_sido");
    }

    @Override
    public List<CenterDTO> searchCenter_address(String centerAddress) throws Exception {
        return searchCenterCommon(null, centerAddress, "searchCenter_address");
    }
}