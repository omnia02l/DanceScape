package org.sid.ebankingbackend.controllers;
import okhttp3.Response;
import org.sid.ebankingbackend.entities.ShazamApiClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import java.util.Base64;



import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/songdetection")
@CrossOrigin("*")
public class ShazamController {

    @PostMapping("/detect-song")
    public ResponseEntity<String> detectSong(@RequestBody String audioBase64) {
        // Decode the base64 encoded audio string
        byte[] audioBytes = Base64.getDecoder().decode(audioBase64);
        System.out.println("Received base64 audio data: " + audioBase64); // Ajout d'un log ici

        // Create a ByteArrayInputStream from the decoded audio bytes
        ByteArrayInputStream audioStream = new ByteArrayInputStream(audioBytes);
        // Convert ByteArrayInputStream back to byte[]
        byte[] audioStreamBytes = audioStream.readAllBytes();

        // Send the request to Shazam API
        try {
            Response response = ShazamApiClient.detectSong(audioStreamBytes);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Shazam API response: " + responseBody); // Ajout d'un log ici
                return ResponseEntity.ok(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error detecting song");
            }
        } catch (IOException e) {
            System.err.println("Error detecting song: " + e.getMessage()); // Ajout d'un log ici
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error detecting song");
        }
    }
}
