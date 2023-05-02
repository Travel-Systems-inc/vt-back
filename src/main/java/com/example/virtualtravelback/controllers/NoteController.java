package com.example.virtualtravelback.controllers;

import com.example.virtualtravelback.models.RestNote;
import com.example.virtualtravelback.services.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/note")
public class NoteController {
    final NoteService noteService;
    NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getNotes(
            @RequestParam(name="author", required = false, defaultValue = "") String authorId) {
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;
        try {
            Object payload = noteService.getNotes(authorId);
            statusCode = 200;
            returnVal.put("notes",payload);
        } catch (ExecutionException | InterruptedException e) {
            returnVal.put("Cannot fetch notes from database.", e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @PostMapping("/")
    public ResponseEntity<Map<String,Object>> createNote(@RequestBody RestNote user){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            Object payload = noteService.createNote(user);
            statusCode = 201;
            returnVal.put("noteId",payload);
        } catch (ExecutionException | InterruptedException e) {
            returnVal.put("Cannot create new note in database.", e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @PutMapping("/{noteId}")
    public ResponseEntity<Map<String,Object>> updateNote(@PathVariable(name="noteId") String id, @RequestBody Map<String,Object> updateValues){
        Map<String, Object> returnVal = new HashMap<>();
        int statusCode = 500;
        try{
            noteService.updateNote(id,updateValues);
            statusCode = 201;
            returnVal.put("Update successful for note with id "+id,"");
        } catch (ParseException e){
            statusCode = 400;
            returnVal.put("Cannot parse JSON",e.getStackTrace());
        } catch (Exception e) {
            returnVal.put("Cannot update note with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Map<String,Object>> removeNote(@PathVariable(name="noteId") String id){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            noteService.deleteNote(id);
            statusCode = 204;
            returnVal.put("Delete successful for note with id" + id,"");
        } catch (Exception e) {
            returnVal.put("Cannot delete note with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
}
