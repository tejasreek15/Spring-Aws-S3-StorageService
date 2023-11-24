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
    public ResponseEntity<String> deleteFile(@RequestParam String bucketName, String key) {
        return new ResponseEntity<>(storageService.deleteFile(bucketName, key), HttpStatus.OK);
    }

    /*
    This api for getting the s3 file url using s3 presigner.
    The presigned URL has an expiration time and can be used to grant temporary access to the S3 object.
     */
    @GetMapping("/getFileUrl")
    public ResponseEntity<String> getUrlForS3Object(@RequestParam String fileName) {
        return new ResponseEntity<>(storageService.getUrlForS3Object(fileName), HttpStatus.OK);
    }

    /*
    This api for getting the s3 file url.
    The regular URL can be used to access the S3 object without any time restrictions.
     */
    @GetMapping("/getObjectFileUrl")
    public ResponseEntity<String> getObjectUrlForS3Object(@RequestParam String fileName) {
        return new ResponseEntity<>(storageService.getObjectUrlForS3Object(fileName), HttpStatus.OK);
    }
}
