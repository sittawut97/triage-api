package com.triage.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "calllog", catalog = "TriageNew", schema = "dbo")
public class CallLog {
    
    // ตาราง calllog ไม่มี primary key ใช้ req เป็น id
    @Id
    @Column(name = "req")
    private String req;
    
    @Column(name = "timereq")
    private LocalDateTime timereq;
    
    @Column(name = "get")
    private String get;
    
    @Column(name = "timeget")
    private LocalDateTime timeget;
    
    // Constructors
    public CallLog() {}
    
    public CallLog(String req, String get) {
        this.setReq(req);  // ใช้ setter เพื่อ validate ความยาว
        this.setGet(get);  // ใช้ setter เพื่อ validate ความยาว
        this.timereq = LocalDateTime.now();
    }
    
    public CallLog(String req) {
        this.setReq(req);  // ใช้ setter เพื่อ validate ความยาว
        this.timereq = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() {
        return req;  // req เป็น id
    }
    
    public void setId(String id) {
        this.req = id;  // req เป็น id
    }
    
    public String getReq() {
        return req;
    }
    
    public void setReq(String req) {
        // จำกัดความยาวไม่เกิน 10 ตัวอักษร (ตาม varchar(10))
        this.req = (req != null && req.length() > 10) ? req.substring(0, 10) : req;
    }
    
    public LocalDateTime getTimereq() {
        return timereq;
    }
    
    public void setTimereq(LocalDateTime timereq) {
        this.timereq = timereq;
    }
    
    public String getGet() {
        return get;
    }
    
    public void setGet(String get) {
        // จำกัดความยาวไม่เกิน 10 ตัวอักษร (ตาม varchar(10))
        this.get = (get != null && get.length() > 10) ? get.substring(0, 10) : get;
    }
    
    public LocalDateTime getTimeget() {
        return timeget;
    }
    
    public void setTimeget(LocalDateTime timeget) {
        this.timeget = timeget;
    }
    
    @PrePersist
    protected void onCreate() {
        if (timereq == null) {
            timereq = LocalDateTime.now();
        }
    }
}
