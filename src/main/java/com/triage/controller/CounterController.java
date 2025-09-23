package com.triage.controller;

import com.triage.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CounterController {
    
    @Autowired
    private CounterService counterService;
    
    // Get all counters
    @GetMapping("/counters")
    public ResponseEntity<List<String>> getCounters() {
        try {
            List<String> counters = counterService.getAllCounters();
            return ResponseEntity.ok(counters);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}