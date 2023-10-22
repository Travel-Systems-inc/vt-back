package com.example.virtualtravelback.security;

import com.example.virtualtravelback.VirtualTravelBackApplication;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        ClassLoader loader = VirtualTravelBackApplication.class.getClassLoader();

        //opens the file stored in resource
        InputStream file = loader.getResourceAsStream("serviceAccountKey.json");
        if (file == null) {
            throw new FileNotFoundException("Service account key file not found.");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(file))
                .build();

        return FirebaseApp.initializeApp(options);
    }
    @Bean
    public Firestore firestore(FirebaseApp firebaseApp) {
        return FirestoreClient.getFirestore(firebaseApp);
    }
    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp){
        return FirebaseAuth.getInstance(firebaseApp);
    }
}