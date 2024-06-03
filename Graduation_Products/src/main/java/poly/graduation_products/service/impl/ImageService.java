package poly.graduation_products.service.impl;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import poly.graduation_products.config.S3Config;

import java.io.File;
import java.io.IOException;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Config s3Config;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String localLocation = "C:\\Users\\data8316-05\\Desktop\\S3_Newstar";

    public String imageUpload(MultipartRequest request) throws IOException {

        //request 인자에서 이미지 파일을 뽑아냄
        MultipartFile file = request.getFile("upload");

        // 뽑아낸 이미지 파일에서 이름 및 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        // 이미지 파일을 이름 유일성을 위해 uuid 생성
        String uuidFileName = UUID.randomUUID() + ext;
        String localPath = localLocation + uuidFileName;

        // 서버 환경에 이미지 파일을 저장
        File localFile = new File(localPath);
        file.transferTo(localFile);


        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();

        localFile.delete();

        return s3Url;
    }
}