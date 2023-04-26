package com.example.virtualtravelback.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class RestNote extends BaseNote{
    private DocumentReference author;

    public RestNote(@Nullable String noteId, String description, @Nullable Timestamp createdAt, GeoPoint location, DocumentReference author) {
        super(noteId, description, createdAt, location);
        this.author = author;
    }

    void setAuthor(String userId){
        Firestore db = FirestoreClient.getFirestore();
    }
}
