package com.dtao.booking_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking_requests")
public class BookingRequest {

    @Id
    private String id;

    private String hallName;       // Auditorium, Falconry, etc
    private String slot;           // Morning / Afternoon / Full
    private String slotTitle;      // Title for slot (event name)
    private String bookingName;    // Requested by (teacher/HOD)
    private String email;          // requester email
    private String department;     // dept info (UG-CSE-1 etc)
    private String phone;          // phone number
    private String date;           // YYYY-MM-DD
    private String startTime;      // optional time field
    private String endTime;        // optional time field
    private String status;         // PENDING / APPROVED / REJECTED
    private String requestedBy;    // userId or dept login

    public BookingRequest() {}

    // constructor for all fields
    public BookingRequest(String hallName, String slot, String slotTitle, String bookingName,
                          String email, String department, String phone, String date,
                          String startTime, String endTime,
                          String status, String requestedBy) {
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
        this.status = status;
        this.requestedBy = requestedBy;
    }

    // ✅ getters + setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }

    public String getSlot() { return slot; }
    public void setSlot(String slot) { this.slot = slot; }

    public String getSlotTitle() { return slotTitle; }
    public void setSlotTitle(String slotTitle) { this.slotTitle = slotTitle; }

    public String getBookingName() { return bookingName; }
    public void setBookingName(String bookingName) { this.bookingName = bookingName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequestedBy() { return requestedBy; }
    public void setRequestedBy(String requestedBy) { this.requestedBy = requestedBy; }
}
