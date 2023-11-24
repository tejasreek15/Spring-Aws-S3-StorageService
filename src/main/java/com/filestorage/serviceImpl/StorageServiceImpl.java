package com.filestorage.serviceImpl;

import com.filestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

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


}
