package com.example.virtualtravelback.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Note extends BaseNote{
    private User author;
    public Note(@Nullable String noteId, String title, @Nullable Timestamp createdAt, GeoPoint location, User author) {
        super(noteId, title, createdAt, location);
        this.author = author;
    }
}
