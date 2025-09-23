package com.triage.service;

import com.triage.entity.Triage;
import com.triage.repository.TriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TriageService {
    
    @Autowired
    private TriageRepository triageRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    // Get all triage column names
    public List<String> getAllColumnNames() {
        return triageRepository.getAllColumnNames();
    }
    
    // Get values for specific column using dynamic SQL
    public List<Map<String, Object>> getValuesByColumn(String columnName) {
        try {
            String sql = "SELECT Number, [" + columnName + "] FROM [TriageNew].[dbo].[triage] " +
                        "WHERE [" + columnName + "] IS NOT NULL AND [" + columnName + "] != '' " +
                        "ORDER BY Number";
            
            Query query = entityManager.createNativeQuery(sql);
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            return results.stream()
                    .map(row -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("number", row[0]);
                        map.put("value", row[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("ไม่สามารถดึงข้อมูล column " + columnName + ": " + e.getMessage());
        }
    }
    
    // Delete triage record using dynamic SQL
    public Map<String, Object> deleteTriageRecord(String columnName, String value, Integer number) {
        try {
            String sql = "DELETE FROM [TriageNew].[dbo].[triage] WHERE [" + columnName + "] = ? AND Number = ?";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, value);
            query.setParameter(2, number);
            int deletedCount = query.executeUpdate();
            
            Map<String, Object> result = new HashMap<>();
            result.put("deletedCount", deletedCount);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("ไม่สามารถลบข้อมูล: " + e.getMessage());
        }
    }
    
    // Check if record has null values
    public Map<String, Object> checkNullValues(Integer number) {
        Integer hasNull = triageRepository.checkHasNullValues(number);
        Map<String, Object> result = new HashMap<>();
        result.put("hasNull", hasNull != null && hasNull == 1);
        return result;
    }
    
    // Update triage column for latest null record
    public Map<String, Object> updateTriageColumn(Integer number, String columnName, String value) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Optional<Triage> triageOpt;
            
            if (number != null) {
                triageOpt = triageRepository.findById(number);
            } else {
                // Find the latest record with null value in the specified column using dynamic SQL
                String sql = "SELECT TOP 1 * FROM [TriageNew].[dbo].[triage] WHERE [" + columnName + "] IS NULL ORDER BY Number DESC";
                Query query = entityManager.createNativeQuery(sql, Triage.class);
                @SuppressWarnings("unchecked")
                List<Triage> results = query.getResultList();
                triageOpt = results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
            }
            
            if (triageOpt.isPresent()) {
                Triage triage = triageOpt.get();
                setColumnValue(triage, columnName, value);
                triageRepository.save(triage);
                
                result.put("success", true);
                result.put("updatedNumber", triage.getNumber());
            } else {
                result.put("success", false);
                result.put("message", "ไม่พบ record ที่ต้องการอัปเดต");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "เกิดข้อผิดพลาดในการอัปเดต: " + e.getMessage());
        }
        
        return result;
    }
    
    // Insert new triage record
    public Map<String, Object> insertTriageRecord(Map<String, String> data) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Triage triage = new Triage();
            
            // Set values for each column
            for (Map.Entry<String, String> entry : data.entrySet()) {
                setColumnValue(triage, entry.getKey(), entry.getValue());
            }
            
            Triage savedTriage = triageRepository.save(triage);
            
            result.put("success", true);
            result.put("number", savedTriage.getNumber());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "เกิดข้อผิดพลาดในการเพิ่มข้อมูล: " + e.getMessage());
        }
        
        return result;
    }
    
    // Helper method to set column value with truncation to prevent SQL errors
    private void setColumnValue(Triage triage, String columnName, String value) {
        try {
            // จำกัดความยาวของข้อมูลไม่ให้เกิน 10 ตัวอักษร (ตามโครงสร้างตาราง varchar(10))
            String truncatedValue = (value != null && value.length() > 10) ? value.substring(0, 10) : value;
            
            switch (columnName.toLowerCase()) {
                case "triage1":
                    triage.setTriage1(truncatedValue);
                    break;
                case "triage5":
                    triage.setTriage5(truncatedValue);
                    break;
                case "triage9":
                    triage.setTriage9(truncatedValue);
                    break;
                case "triage10":
                    triage.setTriage10(truncatedValue);
                    break;
                case "triage11":
                    triage.setTriage11(truncatedValue);
                    break;
                case "triage12":
                    triage.setTriage12(truncatedValue);
                    break;
                case "triages":
                    triage.setTriageS(truncatedValue);
                    break;
                default:
                    throw new IllegalArgumentException("ไม่รองรับ column: " + columnName);
            }
        } catch (Exception e) {
            throw new RuntimeException("ไม่สามารถตั้งค่า column " + columnName + ": " + e.getMessage());
        }
    }
    
}
