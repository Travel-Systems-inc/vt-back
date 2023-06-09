package com.example.virtualtravelback.models;

import com.example.virtualtravelback.util.GeoPointDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseNote {
    @DocumentId
    protected @Nullable String noteId;
    protected String title;
    protected @Nullable Timestamp createdAt;
    @JsonDeserialize(using = GeoPointDeserializer.class)
    protected GeoPoint location;

    public void setCreatedAt(@Nullable Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
