package com.example.virtualtravelback.controllers;

import com.example.virtualtravelback.models.RestBaseDTO;
import com.example.virtualtravelback.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
//something different
@RestController
@RequestMapping("/api/base")
public class BaseController {
    final BaseService baseService;
    BaseController(BaseService baseService){
        this.baseService = baseService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getBases(
        @RequestParam(name="owner", required = false, defaultValue = "") String ownerId,
        @RequestParam(name="limit",required = false, defaultValue = "0") int limit) {
            Map<String,Object> returnVal = new HashMap<>();//yabadabadoo
            int statusCode = 500;
            try {
                Object payload = baseService.getBases(ownerId,limit);
                statusCode = 200;
                returnVal.put("bases",payload);
            } catch (ExecutionException | InterruptedException e) {
                //e.printStackTrace();
                returnVal.put("Cannot fetch bases from database.", e.getStackTrace());
            }
            return ResponseEntity.status(statusCode).body(returnVal);
    }
    @GetMapping("/{baseId}")
    public ResponseEntity<Map<String,Object>> getBaseById(@PathVariable(name="baseId") String id){
        Map<String, Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            Object payload = baseService.getBase(id);
            statusCode = 200;
            returnVal.put("users",payload);
        } catch (ExecutionException | InterruptedException e) {
            returnVal.put("error",e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @PostMapping("/")
    public ResponseEntity<Map<String,Object>> createBase(@RequestBody RestBaseDTO base){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            Object payload = baseService.createBase(base);
            statusCode = 201;
            returnVal.put("baseId",payload);
        } catch (ExecutionException | InterruptedException e) {
            returnVal.put("Cannot create new base in database.", e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @PutMapping("/{baseId}")
    public ResponseEntity<Map<String,Object>> updateBase(@PathVariable(name="baseId") String id, @RequestBody Map<String,Object> updateValues){
        Map<String, Object> returnVal = new HashMap<>();
        int statusCode = 500;
        try{
            baseService.updateBase(id,updateValues);
            statusCode = 201;
            returnVal.put("Update very really successful for base with id "+id,"");
        } catch (ParseException e){
            statusCode = 400;
            returnVal.put("Cannot parse JSON",e.getStackTrace());
        } catch (Exception e) {
            e.printStackTrace();
            returnVal.put("Cannot update base with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
    @DeleteMapping("/{baseId}")
    public ResponseEntity<Map<String,Object>> removeBase(@PathVariable(name="baseId") String id){
        Map<String,Object> returnVal = new HashMap<>();
        int statusCode = 500;

        try{
            baseService.deleteBase(id);
            statusCode = 204;
            returnVal.put("Delete successful for base with id" + id,"");
        } catch (Exception e) {
            returnVal.put("Cannot delete base with id "+id, e.getStackTrace());
        }
        return ResponseEntity.status(statusCode).body(returnVal);
    }
}
