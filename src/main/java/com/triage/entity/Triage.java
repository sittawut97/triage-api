package com.triage.entity;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "triage", catalog = "TriageNew", schema = "dbo")
public class Triage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Number")
    private Integer number;
    
    @Column(name = "Triage1")
    private String triage1;
    
    @Column(name = "Triage5")
    private String triage5;
    
    @Column(name = "Triage9")
    private String triage9;
    
    @Column(name = "Triage10")
    private String triage10;
    
    @Column(name = "Triage11")
    private String triage11;
    
    @Column(name = "Triage12")
    private String triage12;
    
    @Column(name = "TriageS")
    private String triageS;
    
    // Additional dynamic columns can be stored here (ปิดการใช้งานชั่วคราวเพื่อป้องกัน error)
    // @ElementCollection
    // @CollectionTable(name = "triage_dynamic_columns", joinColumns = @JoinColumn(name = "triage_number"))
    // @MapKeyColumn(name = "column_name")
    // @Column(name = "column_value")
    @Transient
    private Map<String, String> dynamicColumns = new HashMap<>();
    
    // Constructors
    public Triage() {}
    
    public Triage(Integer number) {
        this.number = number;
    }
    
    // Getters and Setters
    public Integer getNumber() {
        return number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }
    
    public String getTriage1() {
        return triage1;
    }
    
    public void setTriage1(String triage1) {
        this.triage1 = triage1;
    }
    
    public String getTriage5() {
        return triage5;
    }
    
    public void setTriage5(String triage5) {
        this.triage5 = triage5;
    }
    
    public String getTriage9() {
        return triage9;
    }
    
    public void setTriage9(String triage9) {
        this.triage9 = triage9;
    }
    
    public String getTriage10() {
        return triage10;
    }
    
    public void setTriage10(String triage10) {
        this.triage10 = triage10;
    }
    
    public String getTriage11() {
        return triage11;
    }
    
    public void setTriage11(String triage11) {
        this.triage11 = triage11;
    }
    
    public String getTriage12() {
        return triage12;
    }
    
    public void setTriage12(String triage12) {
        this.triage12 = triage12;
    }
    
    public String getTriageS() {
        return triageS;
    }
    
    public void setTriageS(String triageS) {
        this.triageS = triageS;
    }
    
    public Map<String, String> getDynamicColumns() {
        return dynamicColumns;
    }
    
    public void setDynamicColumns(Map<String, String> dynamicColumns) {
        this.dynamicColumns = dynamicColumns;
    }
    
    // Helper method to get column value by name
    public String getColumnValue(String columnName) {
        switch (columnName.toLowerCase()) {
            case "triage1": return triage1;
            case "triage5": return triage5;
            case "triage9": return triage9;
            case "triage10": return triage10;
            case "triage11": return triage11;
            case "triage12": return triage12;
            case "triages": return triageS;
            default: return dynamicColumns.get(columnName);
        }
    }
    
    // Helper method to set column value by name
    public void setColumnValue(String columnName, String value) {
        // จำกัดความยาวของข้อมูลไม่ให้เกิน 10 ตัวอักษร
        String truncatedValue = (value != null && value.length() > 10) ? value.substring(0, 10) : value;
        
        switch (columnName.toLowerCase()) {
            case "triage1": this.triage1 = truncatedValue; break;
            case "triage5": this.triage5 = truncatedValue; break;
            case "triage9": this.triage9 = truncatedValue; break;
            case "triage10": this.triage10 = truncatedValue; break;
            case "triage11": this.triage11 = truncatedValue; break;
            case "triage12": this.triage12 = truncatedValue; break;
            case "triages": this.triageS = truncatedValue; break;
            default: 
                // ไม่รองรับ dynamic columns ในขณะนี้
                throw new IllegalArgumentException("ไม่รองรับ column: " + columnName);
        }
    }
    
    // Helper method to check if critical columns have null values
    public boolean hasNullValues() {
        return triage1 == null || triage5 == null || triage9 == null || 
               triage10 == null || triage11 == null || triage12 == null || triageS == null;
    }
}
