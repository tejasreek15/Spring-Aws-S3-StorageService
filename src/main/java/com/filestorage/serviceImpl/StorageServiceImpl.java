package com.filestorage.serviceImpl;

import com.filestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3Presigner s3Presigner;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getInputStream().available());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();
        s3Client.putObject(putObjectRequest, requestBody);
        return "File uploaded: " + fileName;
    }

    @Override
    public String deleteFile(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return "Deleted Successfully";
    }

    @Override
    public String getUrlForS3Object(String fileName) {
        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(r -> r.getObjectRequest(get -> get.bucket(bucketName).key(fileName))
                .signatureDuration(Duration.ofMinutes(15)));
        return presignedGetObjectRequest.url().toString();
    }

    @Override
    public String getObjectUrlForS3Object(String fileName) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build())
                .toExternalForm();
    }


}
