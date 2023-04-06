package com.example.virtualtravelback.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @DocumentId
    private @Nullable String userId;
    private @Nullable Timestamp createdAt;
    private String email;
    private int level;
    private String name;
    private boolean visible;
    private ArrayList<String> friends;
    private ArrayList<String> requests;

    public boolean getVisible(){ return visible; }
    public void setCreatedAt(@Nullable Timestamp createdAt){
        this.createdAt = createdAt;
    }
}

hhh

