package com.example.virtualtravelback.models;

import com.example.virtualtravelback.util.GeoPointDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestBaseDTO {
    private @Nullable String baseId;
    private String name;
    private String type;
    private @Nullable Timestamp createdAt;
    @JsonDeserialize(using = GeoPointDeserializer.class)
    private GeoPoint center;
    private ArrayList<String> members;
}
