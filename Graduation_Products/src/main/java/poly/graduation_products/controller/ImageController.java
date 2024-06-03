package poly.graduation_products.controller;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import poly.graduation_products.config.S3Config;
import poly.graduation_products.service.impl.ImageService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/image")
@Controller
public class ImageController {

    private final ImageService imageService;

    @PostMapping("upload")
    @ResponseBody
    public Map<String, Object> imageUpload(MultipartRequest request) throws Exception {

        Map<String, Object> responseData = new HashMap<>();

        try {

            String s3Url = imageService.imageUpload(request);

            responseData.put("uploaded", true);
            responseData.put("url", s3Url);

            return responseData;

        } catch (IOException e) {

            responseData.put("uploaded", false);

            return responseData;
        }

        }
}
