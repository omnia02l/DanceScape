package org.sid.ebankingbackend.entities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class ShazamApiClient {


    private static final String SHAZAM_API_URL = "https://shazam.p.rapidapi.com/songs/detect";
    private static final String RAPID_API_KEY = "ffd564d803msheece92dc4c1f470p1f413djsncf690cadf940";
    private static final String RAPID_API_HOST = "shazam.p.rapidapi.com";

    public static Response detectSong(byte[] audioBytes) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(SHAZAM_API_URL)
                .header("Content-Type", "text/plain")
                .header("X-RapidAPI-Key", RAPID_API_KEY)
                .header("X-RapidAPI-Host", RAPID_API_HOST)
                .post(RequestBody.create(audioBytes))
                .build();

        return client.newCall(request).execute();
    }
}
