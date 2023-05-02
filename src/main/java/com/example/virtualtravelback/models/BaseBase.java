package com.example.virtualtravelback.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseBase {
    @DocumentId
    protected @Nullable String baseId;
    protected String name;
    protected String type;
    protected @Nullable Timestamp createdAt;
    protected GeoPoint center;

    public void setCreatedAt(@Nullable Timestamp createdAt) { this.createdAt = createdAt; }
}
