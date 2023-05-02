package com.example.virtualtravelback.models;

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
    protected String description;
    protected @Nullable Timestamp createdAt;
    protected GeoPoint location;

    public void setCreatedAt(Timestamp createdAt){
        this.createdAt = createdAt;
    }
}
