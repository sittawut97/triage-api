package com.triage.repository;

import com.triage.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounterRepository extends JpaRepository<Counter, Long> {
    
    // Get all counter values - ใช้ข้อมูล mock เนื่องจากไม่มีตาราง cs ในฐานข้อมูล TriageNew
    @Query(value = """
        SELECT 'Triage 1' AS counter_value
        UNION ALL SELECT 'Triage 5'
        UNION ALL SELECT 'Triage 9'
        UNION ALL SELECT 'Triage 10'
        UNION ALL SELECT 'Triage 11'
        UNION ALL SELECT 'Triage 12'
        UNION ALL SELECT 'Triage S'
        """, nativeQuery = true)
    List<String> getAllCounters();
}
