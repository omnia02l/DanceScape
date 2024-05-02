package org.sid.ebankingbackend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload.";
        }

        // Define the directory where you want to store the uploaded images
        String uploadDir = "C:/Users/111fa/OneDrive/Bureau/Dance-merge/src/assets/image";

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + "/" + file.getOriginalFilename());
            Files.write(path, bytes);
            return "File uploaded successfully.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        }
    }*/
