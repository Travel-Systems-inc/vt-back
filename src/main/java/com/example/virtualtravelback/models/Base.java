package com.example.virtualtravelback.models;

<<<<<<< HEAD



public class Base {
}
=======
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.database.annotations.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Base extends BaseBase{
    private User owner;
    private ArrayList<User> members;

    public Base(@Nullable String baseId, String name, String type, @Nullable Timestamp createdAt, GeoPoint center, User owner, ArrayList<User> members) {
        super(baseId, name, type, createdAt, center);
        this.owner = owner;
        this.members = members;
    }
}
>>>>>>> origin/main
