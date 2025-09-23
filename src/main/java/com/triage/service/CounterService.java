package com.triage.service;

import com.triage.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterService {
    
    @Autowired
    private CounterRepository counterRepository;
    
    // Get all counters
    public List<String> getAllCounters() {
        return counterRepository.getAllCounters();
    }
}
