package com.triage.repository;

import com.triage.entity.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CallLogRepository extends JpaRepository<CallLog, String> {
    
    // Get today's call logs
    @Query(value = """
        SELECT req, [get]
        FROM [TriageNew].[dbo].[calllog]
        WHERE CAST(timereq AS DATE) = CAST(GETDATE() AS DATE)
        ORDER BY timereq DESC
        """, nativeQuery = true)
    List<Object[]> getTodayCallLogs();
    
    // Find first record where get is null
    @Query("SELECT c FROM CallLog c WHERE c.get IS NULL ORDER BY c.timereq ASC")
    Optional<CallLog> findFirstByGetIsNull();
    
    // Find first record where get equals specific value and timeget is null
    @Query("SELECT c FROM CallLog c WHERE c.get = :getValue AND c.timeget IS NULL ORDER BY c.timereq ASC")
    Optional<CallLog> findFirstByGetAndTimegetIsNull(@Param("getValue") String getValue);
    
    // Update get field for first null record
    @Modifying
    @Transactional
    @Query(value = "UPDATE TOP(1) [TriageNew].[dbo].[calllog] SET [get] = :getValue, timeget = CURRENT_TIMESTAMP WHERE [get] IS NULL", nativeQuery = true)
    int updateFirstNullGet(@Param("getValue") String getValue);
    
    // Update timeget for first matching record
    @Modifying
    @Transactional
    @Query(value = "UPDATE TOP(1) [TriageNew].[dbo].[calllog] SET timeget = CURRENT_TIMESTAMP WHERE [get] = :getValue AND timeget IS NULL", nativeQuery = true)
    int updateFirstTimeget(@Param("getValue") String getValue);
}
