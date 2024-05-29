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


    @Override
    public List<MedicineDTO> fetchMedicineInfo(MedicineDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + " fetchMedicineInfo Start (의약품 효능 검색)");

        String itemName = pDTO.itemName();
        log.info("itemName : {}", itemName);

//        String apiUrl = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList";
//        String urlParameters = String.format("serviceKey=%s&itemName=%s&type=json",
//                URLEncoder.encode(ServiceKey, "UTF-8"),
//                URLEncoder.encode(itemName, "UTF-8"));
//
//        URL url = new URL(apiUrl + "?" + urlParameters);
//        log.info("URL : {}", url);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + ServiceKey);  // Insert your actual key here
        urlBuilder.append("&" + URLEncoder.encode("itemName", "UTF-8") + "=" + URLEncoder.encode(itemName, "UTF-8"));
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
                    .itemName(itemNode.path("itemName").asText(null))
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
        log.info(this.getClass().getName() + " fetchMedicineInfo End");
        return rList;
    }
}