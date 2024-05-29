package poly.graduation_products.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import poly.graduation_products.dto.MedicineDTO;
import poly.graduation_products.service.IMedicineService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicineService implements IMedicineService {

    @Value("${data.go.kr.encodingKey}")
    private String ServiceKey;


    /*
    식품의약품안전처_의약품 제품 허가정보 API
     */
    @Override
    public List<MedicineDTO> itemNameSearch(String itemName) throws Exception {
        log.info(this.getClass().getName() + "의약품 이름으로 찾기 실행");

        log.info("itemName : {}", itemName);

//        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList");
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnInq05");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // 키 값
        urlBuilder.append("&" + URLEncoder.encode("item_name", "UTF-8") + "=" + URLEncoder.encode(itemName, "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/

        URL url = new URL(urlBuilder.toString());

        log.info("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        log.info("Response code : {}", responseCode);

        BufferedReader rd = responseCode >= 200 && responseCode <= 300 ?
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());

        if (!"00".equals(rootNode.path("header").path("resultCode").asText())) {
            throw new RuntimeException("API call failed: " + rootNode.path("header").path("resultMsg").asText());
        }

        List<MedicineDTO> rList = new ArrayList<>();
        rootNode.path("body").path("items").forEach(itemNode -> {
//            rList.add(MedicineDTO.builder()
//                    .entpName(itemNode.path("entpName").asText(null))
//                    .itemName(itemNode.path("itemName").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
//                    .itemSeq(itemNode.path("itemSeq").asText(null))
//                    .eficacyQesitm(itemNode.path("efcyQesitm").asText(null))
//                    .useMethodQesitm(itemNode.path("useMethodQesitm").asText(null))
//                    .atpnWarnQesitm(itemNode.path("atpnWarnQesitm").asText(null))
//                    .atpnQesitm(itemNode.path("atpnQesitm").asText(null))
//                    .intrcQesitm(itemNode.path("intrcQesitm").asText(null))
//                    .seQesitm(itemNode.path("seQesitm").asText(null))
//                    .depositMethodQesitm(itemNode.path("depositMethodQesitm").asText(null))
//                    .openDe(itemNode.path("openDe").asText(null))
//                    .updateDe(itemNode.path("updateDe").asText(null))
//                    .build());

            rList.add(MedicineDTO.builder()
                    .itemName(itemNode.path("ITEM_NAME").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
                    .entpName(itemNode.path("ENTP_NAME").asText(null))
                    .itemSeq(itemNode.path("ITEM_SEQ").asText(null))
                    .build());
        });

        log.info("Number of items fetched: {}", rList.size());
        log.info(this.getClass().getName() + "의약품 이름으로 찾기 종료");
        return rList;
    }

    /*
    식품의약품안전처_의약품 제품 허가정보 API
     */
    @Override
    public List<MedicineDTO> entpNameSearch(String entpName) throws Exception {
        log.info(this.getClass().getName() + "업체 이름으로 찾기 실행");

        log.info("itemName : {}", entpName);

        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnInq05");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // 키 값
        urlBuilder.append("&" + URLEncoder.encode("entp_name", "UTF-8") + "=" + URLEncoder.encode(entpName, "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/

        URL url = new URL(urlBuilder.toString());

        log.info("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        log.info("Response code : {}", responseCode);

        BufferedReader rd = responseCode >= 200 && responseCode <= 300 ?
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());

        if (!"00".equals(rootNode.path("header").path("resultCode").asText())) {
            throw new RuntimeException("API call failed: " + rootNode.path("header").path("resultMsg").asText());
        }

        List<MedicineDTO> rList = new ArrayList<>();
        rootNode.path("body").path("items").forEach(itemNode -> {

            rList.add(MedicineDTO.builder()
                    .itemName(itemNode.path("ITEM_NAME").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
                    .entpName(itemNode.path("ENTP_NAME").asText(null))
                    .itemSeq(itemNode.path("ITEM_SEQ").asText(null))
                    .build());
        });

        log.info("Number of items fetched: {}", rList.size());
        log.info(this.getClass().getName() + "업체 이름으로 찾기 종료");
        return rList;
    }

    /*
    식품의약품안전처_의약품 제품 허가정보 API
     */
    @Override
    public List<MedicineDTO> allSearch() throws Exception {
        log.info(this.getClass().getName() + "API 데이터 전체 찾기 실행");

        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05/getDrugPrdtPrmsnInq05");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // 키 값
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/

        URL url = new URL(urlBuilder.toString());

        log.info("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        log.info("Response code : {}", responseCode);

        BufferedReader rd = responseCode >= 200 && responseCode <= 300 ?
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());

        if (!"00".equals(rootNode.path("header").path("resultCode").asText())) {
            throw new RuntimeException("API call failed: " + rootNode.path("header").path("resultMsg").asText());
        }

        List<MedicineDTO> rList = new ArrayList<>();
        rootNode.path("body").path("items").forEach(itemNode -> {

            rList.add(MedicineDTO.builder()
                    .itemName(itemNode.path("ITEM_NAME").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
                    .entpName(itemNode.path("ENTP_NAME").asText(null))

                    .itemSeq(itemNode.path("ITEM_SEQ").asText(null))
                    .build());
        });

        log.info("Number of items fetched: {}", rList.size());
        log.info(this.getClass().getName() + "API 데이터 전체 찾기 종료");
        return rList;
    }

    @Override
    public List<MedicineDTO> itemSeqSearch(String itemSeq) throws Exception {
        log.info(this.getClass().getName() + "품목기준코드로 찾기 실행");

        log.info("itemName : {}", itemSeq);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService05");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // 키 값
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("item_seq", "UTF-8") + "=" + URLEncoder.encode(itemSeq, "UTF-8")); // 품목기준코드
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/

        URL url = new URL(urlBuilder.toString());

        log.info("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        log.info("Response code : {}", responseCode);

        BufferedReader rd = responseCode >= 200 && responseCode <= 300 ?
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());

        if (!"00".equals(rootNode.path("header").path("resultCode").asText())) {
            throw new RuntimeException("API call failed: " + rootNode.path("header").path("resultMsg").asText());
        }

        List<MedicineDTO> rList = new ArrayList<>();
        rootNode.path("body").path("items").forEach(itemNode -> {

            rList.add(MedicineDTO.builder()
                    .itemName(itemNode.path("ITEM_NAME").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
                    .entpName(itemNode.path("ENTP_NAME").asText(null))
                    .itemSeq(itemNode.path("ITEM_SEQ").asText(null))
                    .build());
        });

        log.info("Number of items fetched: {}", rList.size());
        log.info(this.getClass().getName() + "품목기준코드로 찾기 종료");
        return rList;    }


    /**
     식품의약품안전처_의약품개요정보(e약은요) API
     */
    @Override
    public List<MedicineDTO> detailSearch(String itemSeq) throws Exception {
        log.info(this.getClass().getName() + "의약품 상세보기 실행");

        log.info("itemName : {}", itemSeq);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // 키 값
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); // 의약품 이름
        urlBuilder.append("&" + URLEncoder.encode("itemSeq", "UTF-8") + "=" + URLEncoder.encode(itemSeq, "UTF-8")); // 품목기준코드
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default:xml*/

        URL url = new URL(urlBuilder.toString());

        log.info("url : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        int responseCode = conn.getResponseCode();
        log.info("Response code : {}", responseCode);

        BufferedReader rd = responseCode >= 200 && responseCode <= 300 ?
                new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8)) :
                new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());

        if (!"00".equals(rootNode.path("header").path("resultCode").asText())) {
            throw new RuntimeException("API call failed: " + rootNode.path("header").path("resultMsg").asText());
        }

        List<MedicineDTO> rList = new ArrayList<>();
        rootNode.path("body").path("items").forEach(itemNode -> {
            rList.add(MedicineDTO.builder()
                    .entpName(itemNode.path("entpName").asText(null))
                    .itemName(itemNode.path("itemName").asText(null).split("\\(")[0].trim())    // 괄호 앞 내용만 가져옴
                    .itemSeq(itemNode.path("itemSeq").asText(null))
                    .eficacyQesitm(itemNode.path("efcyQesitm").asText(null))
                    .useMethodQesitm(itemNode.path("useMethodQesitm").asText(null))
                    .atpnWarnQesitm(itemNode.path("atpnWarnQesitm").asText(null))
                    .atpnQesitm(itemNode.path("atpnQesitm").asText(null))
                    .intrcQesitm(itemNode.path("intrcQesitm").asText(null))
                    .seQesitm(itemNode.path("seQesitm").asText(null))
                    .depositMethodQesitm(itemNode.path("depositMethodQesitm").asText(null))
                    .openDe(itemNode.path("openDe").asText(null))
                    .updateDe(itemNode.path("updateDe").asText(null))
                    .build());

        });

        log.info("Number of items fetched: {}", rList.size());
        log.info(this.getClass().getName() + "의약품 상세보기 종료");
        return rList;
    }
}