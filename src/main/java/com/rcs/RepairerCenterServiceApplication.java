package com.rcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
/*import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;*/

@SpringBootApplication
public class RepairerCenterServiceApplication {
/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }*/

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream
                (new ClassPathResource("repairer-center-app-firebase-admins.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder().setCredentials(googleCredentials).build();
        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "repairer-center-app");
        return FirebaseMessaging.getInstance(app);
    }

    public static void main(String[] args) {
        SpringApplication.run(RepairerCenterServiceApplication.class, args);
    }

}
