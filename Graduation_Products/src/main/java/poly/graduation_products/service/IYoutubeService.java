package poly.graduation_products.service;


import poly.graduation_products.dto.YoutubeDTO;

import java.util.List;

public interface IYoutubeService {

    String apiURL = "https://www.googleapis.com/youtube/v3/search";

    List<YoutubeDTO> getYoutueInfo() throws Exception;


}
