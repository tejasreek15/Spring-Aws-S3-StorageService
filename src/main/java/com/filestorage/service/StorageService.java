package com.filestorage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    String uploadFile(MultipartFile file) throws IOException;

    String deleteFile(String bucketName, String key);
}
