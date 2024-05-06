package org.sid.ebankingbackend.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary("cloudinary://215264427133257:4cMahBcbUP8ZPyE6wluHNh2zG1Y@dervk5jlt");
    }
}


