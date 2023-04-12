package com.example.virtualtravelback.models;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class RestBase extends BaseBase{
    private DocumentReference owner;
    private ArrayList<DocumentReference> members;

    public RestBase(String baseId, String name, String type, Timestamp createdAt, GeoPoint center, DocumentReference owner, ArrayList<DocumentReference> members) {
        super(baseId, name, type, createdAt, center);
        this.owner = owner;
        this.members = members;
    }

    public void setOwner(String owner) {
        Firestore db = FirestoreClient.getFirestore();
        this.owner = db.collection("Base").document(owner);
    }

    public void setMembers(ArrayList<String> members){
        Firestore db = FirestoreClient.getFirestore();
        this.members = new ArrayList<>();
        for (String s: members){
            this.members.add(db.collection("Base").document(s));
        }
    }

}