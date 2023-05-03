package com.example.virtualtravelback.controllers;

import com.example.virtualtravelback.services.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/base")
public class BaseController {
    final BaseService baseService;
    BaseController(BaseService baseService){
        this.baseService = baseService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getBases(
        @RequestParam(name="owner", required = false, defaultValue = "") String ownerId) {
            Map<String,Object> returnVal = new HashMap<>();
            int statusCode = 500;
            try {
                Object payload = baseService.getBases(ownerId);
                statusCode = 200;
                returnVal.put("bases",payload);
            } catch (ExecutionException | InterruptedException e) {
                returnVal.put("Cannot fetch bases from database.", e.getStackTrace());
            }
            return ResponseEntity.status(statusCode).body(returnVal);
    }
}
