package com.example.virtualtravelback;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class VirtualTravelBackApplication {

    public static void main(String[] args) throws IOException {
        ClassLoader loader = VirtualTravelBackApplication.class.getClassLoader();

        //opens the file stored in resources
        File file = new File(loader.getResource("serviceAccountKey.json").getFile());
        //reads the data from the file
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        //connect to Firebase
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        SpringApplication.run(VirtualTravelBackApplication.class, args);
    }
}
