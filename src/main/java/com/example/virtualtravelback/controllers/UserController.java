package com.example.virtualtravelback.controllers;

import com.example.virtualtravelback.models.User;
import com.example.virtualtravelback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){ this.userService = userService; }

    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getUsers(){
        Map<String, Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            Object payload = userService.getUsers();
            statusCode = 200;
            returnVal.put("users",payload);
        } catch (ExecutionException | InterruptedException e) {
            returnVal.put("error",e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }

    @PostMapping("/")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody User user){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            Object payload = userService.createUser(user);
            statusCode = 201;
            returnVal.put("userId",payload);
        } catch (ExecutionException | InterruptedException | ParseException e) {
            returnVal.put("Cannot create new user in database.", e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String,Object>> updateUser(@PathVariable(name="userId") String id, @RequestBody Map<String, String> updateValues){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            userService.updateUser(id,updateValues);
            statusCode = 201;
            returnVal.put("Update successful for user with id "+id,"");
        } catch (ParseException e){
            statusCode = 400;
            returnVal.put("Cannot parse JSON",e.getStackTrace());
        } catch (Exception e) {
            returnVal.put("Cannot update user with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String,Object>> removeUser(@PathVariable(name="userId") String id){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            userService.deleteUser(id);
            statusCode = 204;
            returnVal.put("Delete successful for user with id" + id,"");
        } catch (Exception e) {
            returnVal.put("Cannot delete user with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
}
