package com.triage.controller;

import com.triage.service.CallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/calllog")
@CrossOrigin(origins = "*")
public class CallLogController {
    
    @Autowired
    private CallLogService callLogService;
    
    // Get today's call logs
    @GetMapping("/today")
    public ResponseEntity<List<Map<String, Object>>> getTodayCallLogs() {
        try {
            List<Map<String, Object>> callLogs = callLogService.getTodayCallLogs();
            return ResponseEntity.ok(callLogs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Insert new call log
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertCallLog(@RequestBody Map<String, String> request) {
        try {
            String req = request.get("req");
            String get = request.get("get");
            
            Map<String, Object> result = callLogService.insertCallLog(req, get);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Update call log get field
    @PutMapping("/update-get")
    public ResponseEntity<Map<String, Object>> updateCallLogGet(@RequestBody Map<String, String> request) {
        try {
            String columnName = request.get("columnName");
            
            Map<String, Object> result = callLogService.updateCallLogGet(columnName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
