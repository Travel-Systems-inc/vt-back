package com.example.virtualtravelback.services;

import com.example.virtualtravelback.models.Note;
import com.example.virtualtravelback.models.RestNote;
import com.example.virtualtravelback.models.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class NoteService {
    private final Firestore db;
    NoteService(Firestore db){
        this.db = db;
    }
    private Note getNote(DocumentSnapshot doc) throws ExecutionException, InterruptedException {
        UserService userService = new UserService(db);
        User author = userService.getUser((DocumentReference) doc.get("author"));

        return new Note(doc.getId(),
                doc.getString("description"),
                doc.getTimestamp("createdAt"),
                doc.getGeoPoint("location"),
                author);
    }

    public ArrayList<Note> getNotes(String userId) throws ExecutionException, InterruptedException {
        Query q = db.collection("Note");
        if (!userId.isEmpty()) {
            DocumentReference userRef = db.collection("User").document(userId);
            q = q.whereEqualTo("author", userRef);
        }
        ApiFuture<QuerySnapshot> snap = q.get();
        List<QueryDocumentSnapshot> docs = snap.get().getDocuments();

        ArrayList<Note> notes = new ArrayList<>();
        for (QueryDocumentSnapshot doc: docs){
            notes.add(getNote(doc));
        }
        return notes;
    }

    public Note getNote(String id) throws ExecutionException, InterruptedException {
        Note note = null;
        DocumentReference noteDoc = db.collection("Note").document(id);
        ApiFuture<DocumentSnapshot> future = noteDoc.get();
        DocumentSnapshot doc = future.get();
        if(doc.exists())
            note = getNote(doc);
        return note;
    }

    public Note getNote(DocumentReference postRef) throws ExecutionException, InterruptedException {
        DocumentSnapshot doc = postRef.get().get();
        return getNote(doc);
    }

    public String createNote(RestNote note) throws ExecutionException, InterruptedException {
        ApiFuture<DocumentReference> future = db.collection("Note").add(note);
        DocumentReference noteRef = future.get();
        return noteRef.getId();
    }

    public void updateNote(String id, Map<String, Object> updateValues) throws ParseException {
        String [] allowed = {"author","description","location"};
        List<String> list = Arrays.asList(allowed);
        Map<String, Object> formattedValues = new HashMap<>();

        for(Map.Entry<String, Object> entry : updateValues.entrySet())
        {
            String key = entry.getKey();
            if(list.contains(key)) {
                formattedValues.put(key, entry.getValue());
            }
        }
        DocumentReference postDoc = db.collection("Note").document(id);
        postDoc.update(formattedValues);
    }
    public void deleteNote(String postId){
        DocumentReference postDoc = db.collection("Note").document(postId);
        postDoc.delete();
    }
}
