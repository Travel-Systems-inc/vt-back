package com.example.virtualtravelback.services;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    private final Firestore db;
    BaseService(Firestore db){
        this.db = db;
    }

}
