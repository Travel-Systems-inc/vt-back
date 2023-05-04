package com.example.virtualtravelback.services;

import com.example.virtualtravelback.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final Firestore db;
    public UserService(Firestore db){ this.db = db; }

    public ArrayList<User> getUsers() throws ExecutionException, InterruptedException {
        Query query = db.collection("User");
        ApiFuture<QuerySnapshot> future = query.get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        ArrayList<User> users = documents.size() > 0 ? new ArrayList<>() : null;

        for(QueryDocumentSnapshot doc : documents){
            users.add(doc.toObject(User.class));
        }
        return users;
    }

    public User getUser(String userId) throws ExecutionException, InterruptedException {
        DocumentReference doc = db.collection("User").document(userId);
        ApiFuture<DocumentSnapshot> future = doc.get();
        DocumentSnapshot docsnap = future.get();
        if (docsnap.exists())
            return docsnap.toObject(User.class);
        return null;
    }

    public User getUser(DocumentReference userRef) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentSnapshot> userQuery = userRef.get();
        DocumentSnapshot userDoc = userQuery.get();
        return userDoc.toObject(User.class);
    }

    public String createUser(User user) throws ExecutionException, InterruptedException, ParseException {
        user.setCreatedAt(Timestamp.now());

        ApiFuture<DocumentReference> future = db.collection("User").add(user);
        DocumentReference userRef = future.get();
        return userRef.getId();
    }

    public void updateUser(String id, Map<String, String> updateValues) throws ParseException{
        String [] allowed = {"name", "level", "visible", "email", "friends", "requests"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for(Map.Entry<String, String> entry : updateValues.entrySet()) {
            String key = entry.getKey();
            if(list.contains(key))
                formattedValues.put(key, entry.getValue());
        }

        DocumentReference userDoc = db.collection("User").document(id);
        userDoc.update(formattedValues);
    }

    public void deleteUser(String userId){
        DocumentReference userDoc = db.collection("User").document(userId);
        userDoc.delete();
    }
}