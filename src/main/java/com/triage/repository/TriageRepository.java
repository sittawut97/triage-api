package com.triage.repository;

import com.triage.entity.Triage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriageRepository extends JpaRepository<Triage, Integer> {
    
    // Find records by specific column value - ใช้ dynamic SQL ใน service layer แทน
    // Method นี้จะถูกใช้ผ่าน custom implementation
    
    // Get all column names from triage table
    @Query(value = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'triage' AND TABLE_SCHEMA = 'dbo' AND COLUMN_NAME != 'Number'", nativeQuery = true)
    List<String> getAllColumnNames();
    
    // Get values for specific column where not null and not empty - ใช้ dynamic SQL ใน service layer แทน
    // Method นี้จะถูกใช้ผ่าน custom implementation
    
    // Delete records by column and value - ใช้ dynamic SQL ใน service layer แทน
    // Method นี้จะถูกใช้ผ่าน custom implementation
    
    // Find record with null values in critical columns - ใช้ dynamic SQL ใน service layer แทน
    // Method นี้จะถูกใช้ผ่าน custom implementation
    
    // Check if record has null values in critical columns
    @Query(value = """
        SELECT CASE WHEN 
            Triage1 IS NULL OR Triage5 IS NULL OR Triage9 IS NULL OR 
            Triage10 IS NULL OR Triage11 IS NULL OR Triage12 IS NULL OR TriageS IS NULL
        THEN 1 ELSE 0 END as has_null
        FROM [TriageNew].[dbo].[triage]
        WHERE Number = :number
        """, nativeQuery = true)
    Integer checkHasNullValues(@Param("number") Integer number);
}
