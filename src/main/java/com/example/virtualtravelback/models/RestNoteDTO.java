package com.example.virtualtravelback.models;

import com.example.virtualtravelback.util.GeoPointDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestNoteDTO {
    private String noteId;
    private String title;
    private Timestamp createdAt;
    @JsonDeserialize(using = GeoPointDeserializer.class)
    private GeoPoint location;
    private String author;
}