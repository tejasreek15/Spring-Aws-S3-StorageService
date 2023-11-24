package com.filestorage.controller;

import com.filestorage.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/s3")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(storageService.uploadFile(file), HttpStatus.OK);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam String bucketName, String key) throws IOException {
        return new ResponseEntity<>(storageService.deleteFile(bucketName, key), HttpStatus.OK);
    }
}
