package poly.graduation_products.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import poly.graduation_products.dto.YoutubeDTO;
import poly.graduation_products.service.IYoutubeService;
import poly.graduation_products.util.NetworkUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeService implements IYoutubeService {

    @Value("${youtube.api.key}")
    private String apiKey;

    @Override
    public List<YoutubeDTO> getYoutueInfo() throws Exception {

        log.info(this.getClass().getName() + ".getYoutueInfo Start!");

        // 검색어
        String input = "의약품 관리";
        // 검색어 url인코딩 저장할 값
        String encodedString = null;

        // 검색어 url 인코딩
        try {
            encodedString = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 변경 가능 변수
        // char=가장인기있는동영상
        // maxResults = 받아온 리스트 값 현재 값 3 (위에서부터 3개만 받아옴)
        // q= 검색어 (url인코딩)
        // type=video,channel,playlist <- youtube 분류

        String apiParam = "?part=snippet&chart=mostPopular&maxResults=5&q=" + encodedString + "&type=video&key=" + apiKey;

        String json = NetworkUtil.get(IYoutubeService.apiURL + apiParam);

        Map<String, Object> tMap = new ObjectMapper().readValue(json, LinkedHashMap.class);

        List<Map<String, Object>> tContents = (List<Map<String, Object>>) tMap.get("items");

        List<YoutubeDTO> rList = new ArrayList<>();

        log.info("tContents" + tContents);

        for (int i = 0; i < tContents.size(); i++) {

            // id 데이터
            Map<String, Object> iMap = (Map<String, Object>) tContents.get(i).get("id");
            // snitppet 데이터
            Map<String, Object> sMap = (Map<String, Object>) tContents.get(i).get("snippet");
            // 썸네일 데이터
            Map<String, Object> thumMap = (Map<String, Object>) sMap.get("thumbnails");
            Map<String, Object> thumMed = (Map<String, Object>) thumMap.get("medium");

            // 비디오 고유 ID
            String videoId = (String) iMap.get("videoId");
            // 비디오 제목
            String videoTitle = (String) sMap.get("title");
            // 비디오 날짜
            String videoPublished = (String) sMap.get("publishedAt");
            // 상세 내용
            String description = (String) sMap.get("description");
            // 채널 제목
            String channelTitle = (String) sMap.get("channelTitle");
            // 채널 생성일
            String publishTime = (String) sMap.get("publishTime");
            // 썸네일 데이터 중간 크기 썸네일(w320, h180)
            String url = (String) thumMed.get("url");


            // videoPublished 값을 원하는 형식으로 변환
            LocalDateTime publishedDateTime = LocalDateTime.parse(videoPublished, DateTimeFormatter.ISO_DATE_TIME);
            String formattedDate = publishedDateTime.format(DateTimeFormatter.ofPattern("yy-MM-dd"));



            log.info("유튜브 제목 : " +  videoTitle);
            log.info("채널 제목 : " +  channelTitle);
            log.info("영상 날짜 : " +  formattedDate);

            log.info((i+1) + "번째 데이터" + "videoId : " + videoId + " / videoTitle : " + videoTitle + " / videoPublished : " + videoPublished + " / description : " + description + " / url : " + url);

            YoutubeDTO pDTO = YoutubeDTO.builder()
                    .videoId(videoId)
                    .videoTitle(videoTitle)
                    .videoPublished(formattedDate)
                    .description(description)
                    .channelTitle(channelTitle)
                    .publishTime(publishTime)
                    .url(url)
                    .build();

            rList.add(pDTO);
        }

        log.info(this.getClass().getName() + ".getYoutueInfo End!");

        return rList;

    }
}
