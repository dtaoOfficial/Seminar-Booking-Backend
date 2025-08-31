package com.dtao.booking_project.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "seminars")
public class Seminar {
    @Id
    private String id;

    private String hallName;
    private String slot;        // Morning, Afternoon, Full Day
    private String slotTitle;   // why booking
    private String bookingName;
    private String email;
    private String department;  // e.g. UG-CSE-1
    private String phone;
    private String date;        // YYYY-MM-DD
    private String startTime;   // HH:mm
    private String endTime;     // HH:mm
    private String status = "APPROVED"; // Admin adds directly

    public Seminar() {}

    public Seminar(String hallName, String slot, String slotTitle,
                   String bookingName, String email, String department,
                   String phone, String date, String startTime, String endTime) {
        this.hallName = hallName;
        this.slot = slot;
        this.slotTitle = slotTitle;
        this.bookingName = bookingName;
        this.email = email;
        this.department = department;
        this.phone = phone;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = "APPROVED";
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public void setSlotTitle(String slotTitle) {
        this.slotTitle = slotTitle;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// Getters & Setters
    // ... (generate with IDE)
}
