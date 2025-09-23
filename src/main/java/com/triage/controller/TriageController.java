package com.triage.controller;

import com.triage.service.TriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/triage")
@CrossOrigin(origins = "*")
public class TriageController {
    
    @Autowired
    private TriageService triageService;
    
    // Get all triage columns
    @GetMapping("/columns")
    public ResponseEntity<List<String>> getTriageColumns() {
        try {
            List<String> columns = triageService.getAllColumnNames();
            return ResponseEntity.ok(columns);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Get triage values for specific column
    @GetMapping("/values/{columnName}")
    public ResponseEntity<List<Map<String, Object>>> getTriageValues(@PathVariable String columnName) {
        try {
            List<Map<String, Object>> values = triageService.getValuesByColumn(columnName);
            return ResponseEntity.ok(values);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Delete triage record
    @DeleteMapping("/record")
    public ResponseEntity<Map<String, Object>> deleteTriageRecord(@RequestBody Map<String, Object> request) {
        try {
            String columnName = (String) request.get("columnName");
            String value = (String) request.get("value");
            Integer number = (Integer) request.get("number");
            
            Map<String, Object> result = triageService.deleteTriageRecord(columnName, value, number);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Check null values in record
    @GetMapping("/check-nulls/{number}")
    public ResponseEntity<Map<String, Object>> checkNullValues(@PathVariable Integer number) {
        try {
            Map<String, Object> result = triageService.checkNullValues(number);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Update triage column
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateTriageColumn(@RequestBody Map<String, Object> request) {
        try {
            Integer number = (Integer) request.get("number");
            String columnName = (String) request.get("columnName");
            String value = (String) request.get("value");
            
            Map<String, Object> result = triageService.updateTriageColumn(number, columnName, value);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Insert new triage record
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertTriageRecord(@RequestBody Map<String, String> data) {
        try {
            Map<String, Object> result = triageService.insertTriageRecord(data);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}