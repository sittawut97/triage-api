package com.triage.service;

import com.triage.entity.CallLog;
import com.triage.repository.CallLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CallLogService {
    
    @Autowired
    private CallLogRepository callLogRepository;
    
    // Get today's call logs
    public List<Map<String, Object>> getTodayCallLogs() {
        List<Object[]> results = callLogRepository.getTodayCallLogs();
        return results.stream()
                .map(row -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("req", row[0]);
                    map.put("get", row[1]);
                    return map;
                })
                .collect(Collectors.toList());
    }
    
    // Insert new call log
    public Map<String, Object> insertCallLog(String req, String get) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // จำกัดความยาวของข้อมูลก่อนสร้าง entity
            String truncatedReq = (req != null && req.length() > 10) ? req.substring(0, 10) : req;
            String truncatedGet = (get != null && get.length() > 10) ? get.substring(0, 10) : get;
            
            CallLog callLog = new CallLog();
            callLog.setReq(truncatedReq);
            callLog.setGet(truncatedGet);
            callLog.setTimereq(LocalDateTime.now());
            
            if (truncatedGet != null) {
                callLog.setTimeget(LocalDateTime.now());
            }
            
            CallLog savedCallLog = callLogRepository.save(callLog);
            
            result.put("success", true);
            result.put("req", savedCallLog.getId());  // req เป็น id
            
            // แจ้งเตือนถ้าข้อมูลถูกตัด
            if ((req != null && req.length() > 10) || (get != null && get.length() > 10)) {
                result.put("warning", "ข้อมูลบางส่วนถูกตัดให้เหลือ 10 ตัวอักษร");
            }
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "เกิดข้อผิดพลาดในการเพิ่ม call log: " + e.getMessage());
        }
        
        return result;
    }
    
    // Update call log get field
    public Map<String, Object> updateCallLogGet(String columnName) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // First try to update a record where get is null
            int updatedCount = callLogRepository.updateFirstNullGet(columnName);
            
            if (updatedCount == 0) {
                // If no null records found, update timeget for existing record
                updatedCount = callLogRepository.updateFirstTimeget(columnName);
            }
            
            result.put("success", true);
            result.put("updatedCount", updatedCount);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "เกิดข้อผิดพลาดในการอัปเดต call log: " + e.getMessage());
        }
        
        return result;
    }
    
    // Get all call logs (for admin purposes)
    public List<CallLog> getAllCallLogs() {
        return callLogRepository.findAll();
    }
    
    // Get call log by req (id)
    public Optional<CallLog> getCallLogById(String req) {
        return callLogRepository.findById(req);
    }
}
