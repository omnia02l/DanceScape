package org.sid.ebankingbackend.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@RestController
@RequestMapping("/autosong")
@CrossOrigin("*")
public class autosongcontroller {

    private static final String RAPID_API_KEY = "9c0cf58b04mshcfde08190e94343p11bcccjsn785c63ce2d0c";
    private static final String RAPID_API_HOST = "shazam.p.rapidapi.com";

    @GetMapping("/auto-complete")
    public ResponseEntity<?> autoComplete(@RequestParam String term) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://shazam.p.rapidapi.com/auto-complete?term=" + term + "&locale=en-US")
                .get()
                .addHeader("x-rapidapi-key", RAPID_API_KEY)
                .addHeader("x-rapidapi-host", RAPID_API_HOST)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching auto-complete suggestions");
        }
    }
}
