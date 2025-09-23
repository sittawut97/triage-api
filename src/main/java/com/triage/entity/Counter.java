package com.triage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "webform_xphonebook_number", catalog = "TriageNew", schema = "dbo")
public class Counter {
    
    @Id
    @Column(name = "NumberID")
    private Long id;
    
    @Column(name = "Number_Department")
    private String ccounter;
    
    // Constructors
    public Counter() {}
    
    public Counter(String ccounter) {
        this.ccounter = ccounter;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCcounter() {
        return ccounter;
    }
    
    public void setCcounter(String ccounter) {
        this.ccounter = ccounter;
    }
}
