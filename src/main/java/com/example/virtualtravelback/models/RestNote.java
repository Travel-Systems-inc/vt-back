package com.example.virtualtravelback.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.GeoPoint;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class RestNote extends BaseNote{
    private DocumentReference author;

    public RestNote(@Nullable String noteId, String title, @Nullable Timestamp createdAt, GeoPoint location, DocumentReference author) {
        super(noteId, title, createdAt, location);
        this.author = author;
    }

    public void setAuthor(String author, Firestore db){
        this.author = db.collection("User").document(author);
    }
}
