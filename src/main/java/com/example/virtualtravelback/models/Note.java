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
public class Note {

    @DocumentId
    private reference author;

    private @Nullable Timestamp createdAt;


}
