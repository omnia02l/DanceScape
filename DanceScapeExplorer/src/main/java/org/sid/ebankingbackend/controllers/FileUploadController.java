package org.sid.ebankingbackend.controllers;


import org.sid.ebankingbackend.services.CloudinaryService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController

@CrossOrigin("*")
public class FileUploadController {

    private final CloudinaryService1 cloudinaryService;

    @Autowired
    public FileUploadController(CloudinaryService1 cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(uploadResult);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
