package com.example.virtualtravelback.services;

import com.example.virtualtravelback.models.Base;
import com.example.virtualtravelback.models.Note;
import com.example.virtualtravelback.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BaseService {
    private final Firestore db;
    BaseService(Firestore db){
        this.db = db;
    }
    private Base getBase(DocumentSnapshot doc) throws ExecutionException, InterruptedException {
        UserService userService = new UserService(db);
        User owner = userService.getUser((DocumentReference) doc.get("owner"));
        ArrayList<User> members = new ArrayList<>();
        ArrayList<DocumentReference> memRefs = (ArrayList<DocumentReference>) doc.get("members");
        for (DocumentReference ref: memRefs){
            members.add(userService.getUser(ref));
        }

        return new Base(doc.getId(),
                doc.getString("name"),
                doc.getString("type"),
                doc.getTimestamp("createdAt"),
                doc.getGeoPoint("center"),
                owner,members);
    }
    public ArrayList<Base> getBases(String ownerId) throws ExecutionException, InterruptedException {
        Query q = db.collection("Base");
        if (!ownerId.isEmpty()){
            DocumentReference userRef = db.collection("User").document(ownerId);
            q = q.whereEqualTo("owner", userRef);
        }
        ArrayList<Base> bases = new ArrayList<>();

        ApiFuture<QuerySnapshot> snap = q.get();
        List<QueryDocumentSnapshot> docs = snap.get().getDocuments();
        for (QueryDocumentSnapshot doc: docs){
            bases.add(getBase(doc));
        }
        return bases;
    }
}
