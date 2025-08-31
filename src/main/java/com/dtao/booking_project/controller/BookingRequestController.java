package com.dtao.booking_project.controller;

import com.dtao.booking_project.model.BookingRequest;
import com.dtao.booking_project.service.BookingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:3000")  // Allow frontend access
public class BookingRequestController {

    @Autowired
    private BookingRequestService service;

    // --- Create new request ---
    @PostMapping
    public ResponseEntity<BookingRequest> create(@RequestBody BookingRequest request) {
        BookingRequest created = service.createRequest(request);
        return ResponseEntity.ok(created);
    }

    // --- Get all requests (Admin use) ---
    @GetMapping
    public ResponseEntity<List<BookingRequest>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // --- Get by ID ---
    @GetMapping("/{id}")
    public ResponseEntity<BookingRequest> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Search with filters ---
    // Example: /api/requests/search?department=CSE&hall=Auditorium&date=2025-08-30&status=PENDING&sort=latest
    @GetMapping("/search")
    public ResponseEntity<List<BookingRequest>> search(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String hall,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String slot,
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "latest") String sort
    ) {
        return ResponseEntity.ok(service.search(department, hall, date, slot, status, sort));
    }

    // --- Get requests by Email (user side) ---
    @GetMapping("/email/{email}")
    public ResponseEntity<List<BookingRequest>> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    // --- Get requests by Department (dept dashboard) ---
    @GetMapping("/department/{department}")
    public ResponseEntity<List<BookingRequest>> getByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(service.getByDepartment(department));
    }

    // --- Approve request ---
    @PutMapping("/{id}/approve")
    public ResponseEntity<BookingRequest> approve(@PathVariable String id) {
        return ResponseEntity.ok(service.approve(id));
    }

    // --- Reject request ---
    @PutMapping("/{id}/reject")
    public ResponseEntity<BookingRequest> reject(@PathVariable String id) {
        return ResponseEntity.ok(service.reject(id));
    }

    // --- Delete request ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    // ✅ Get user requests filtered by status
    @GetMapping("/user/status")
    public ResponseEntity<List<BookingRequest>> getUserByStatus(
            @RequestParam String email,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(service.getByEmailAndStatus(email, status));
    }

    // ✅ Get user requests by date range
    @GetMapping("/user/dates")
    public ResponseEntity<List<BookingRequest>> getUserByDateRange(
            @RequestParam String email,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return ResponseEntity.ok(service.getByEmailAndDateRange(email, startDate, endDate));
    }


}
