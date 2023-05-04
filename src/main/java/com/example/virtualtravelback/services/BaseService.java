package com.example.virtualtravelback.services;

import com.example.virtualtravelback.models.*;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.text.ParseException;
import java.util.*;
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
    public ArrayList<Base> getBases(String ownerId, int limit) throws ExecutionException, InterruptedException {
        Query q = db.collection("Base").orderBy("name");
        if (!ownerId.isEmpty()){
            DocumentReference userRef = db.collection("User").document(ownerId);
            q = q.whereEqualTo("owner", userRef);
        }
        if (limit > 0)
            q = q.limit(limit);
        ArrayList<Base> bases = new ArrayList<>();

        ApiFuture<QuerySnapshot> snap = q.get();
        List<QueryDocumentSnapshot> docs = snap.get().getDocuments();
        for (QueryDocumentSnapshot doc: docs){
            bases.add(getBase(doc));
        }
        return bases;
    }

    public Base getBase(String id) throws ExecutionException, InterruptedException {
        DocumentReference basedoc = db.collection("Base").document(id);
        ApiFuture<DocumentSnapshot> future = basedoc.get();
        DocumentSnapshot doc = future.get();
        if (doc.exists())
            return getBase(doc);
        return null;
    }

    public String createBase(RestBaseDTO baseDTO) throws ExecutionException, InterruptedException {
        RestBase base = new RestBase(baseDTO.getBaseId(),
                baseDTO.getName(),
                baseDTO.getType(),
                baseDTO.getCreatedAt(),
                baseDTO.getCenter(),
                null, null); // Set owner and members to null initially
        base.setCreatedAt(Timestamp.now());
        base.setOwner(baseDTO.getOwner(), db);
        base.setMembers(baseDTO.getMembers(), db);

        ApiFuture<DocumentReference> future = db.collection("Base").add(base);
        DocumentReference baseRef = future.get();
        return baseRef.getId();
    }
    public void updateBase(String id, Map<String, Object> updateValues) throws ParseException, ExecutionException, InterruptedException {
        String [] allowed = {"owner","addMembers","members","name"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        //retrieve the base document
        DocumentReference baseDoc = db.collection("Base").document(id);
        DocumentSnapshot baseSnapshot = baseDoc.get().get();

        //list current members
        List<DocumentReference> currentMembersList = (List<DocumentReference>) baseSnapshot.get("members");
        Set<String> currentMembers = getIdsFromUserReferences(currentMembersList);

        for(Map.Entry<String, Object> entry : updateValues.entrySet()) {
            String key = entry.getKey();
            if (list.contains(key)) {
                switch (key) {
                    case "addMembers":
                        List<String> newMembers = (List<String>) entry.getValue();
                        for (String newMember : newMembers) {
                            // Check if the user id exists
                            if (userExists(newMember)) {
                                currentMembers.add(newMember);
                            }
                        }
                        formattedValues.put("members", getReferencesFromIds(currentMembers));
                        break;
                    case "members":
                        List<String> updatedMembers = (List<String>) entry.getValue();
                        Set<String> validMembers = new HashSet<>();
                        for (String updatedMember : updatedMembers) {
                            // Check if the user id exists
                            if (userExists(updatedMember)) {
                                validMembers.add(updatedMember);
                            }
                        }
                        formattedValues.put(key, getReferencesFromIds(validMembers));
                        break;
                    case "owner":
                        formattedValues.put(key, db.collection("User").document((String) entry.getValue()));
                        break;
                    default:
                        formattedValues.put(key, entry.getValue());
                }
            }
        }
        baseDoc.update(formattedValues);
    }
    private Set<String> getIdsFromUserReferences(List<DocumentReference> userReferences) {
        Set<String> userIds = new HashSet<>();
        for (DocumentReference userRef : userReferences) {
            userIds.add(userRef.getId());
        }
        return userIds;
    }
    private boolean userExists(String userId) throws InterruptedException, ExecutionException {
        DocumentReference userDoc = db.collection("User").document(userId);
        DocumentSnapshot userSnapshot = userDoc.get().get();
        return userSnapshot.exists();
    }
    private List<DocumentReference> getReferencesFromIds(Set<String> userIds) throws InterruptedException, ExecutionException {
        List<DocumentReference> users = new ArrayList<>();
        for (String userId : userIds) {
            DocumentReference userDoc = db.collection("User").document(userId);
            users.add(userDoc);
        }
        return users;
    }
    public void deleteBase(String postId){
        DocumentReference postDoc = db.collection("Base").document(postId);
        postDoc.delete();
    }
}
