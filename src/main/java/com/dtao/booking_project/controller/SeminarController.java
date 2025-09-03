package com.dtao.booking_project.controller;

import com.dtao.booking_project.model.Seminar;
import com.dtao.booking_project.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seminars")
@CrossOrigin(origins = "http://localhost:3000")
public class SeminarController {

    @Autowired
    private SeminarService seminarService;

    // ✅ Add new seminar
    @PostMapping
    public ResponseEntity<?> createSeminar(@RequestBody Seminar seminar) {
        try {
            return ResponseEntity.ok(seminarService.addSeminar(seminar));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // ✅ Get all seminars
    @GetMapping
    public ResponseEntity<List<Seminar>> getAllSeminars() {
        return ResponseEntity.ok(seminarService.getAllSeminars());
    }

    // ✅ Get seminars by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Seminar>> getSeminarsByDate(@PathVariable String date) {
        return ResponseEntity.ok(seminarService.getSeminarsByDate(date));
    }

    // ✅ Get seminars by hall & date
    // SeminarController.java
    @GetMapping("/hall/{hallName}/date/{date}")
    public ResponseEntity<List<Seminar>> getByHallAndDate(
            @PathVariable String hallName,
            @PathVariable String date) {
        return ResponseEntity.ok(seminarService.getByHallAndDate(date, hallName));
    }


    // ✅ Update seminar
    @PutMapping("/{id}")
    public ResponseEntity<Seminar> updateSeminar(
            @PathVariable String id,
            @RequestBody Seminar updatedSeminar) {
        Seminar seminar = seminarService.updateSeminar(id, updatedSeminar);
        return seminar != null ? ResponseEntity.ok(seminar) : ResponseEntity.notFound().build();
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeminar(@PathVariable String id) {
        seminarService.deleteSeminar(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Dept History (filter by department + email)
    @GetMapping("/history")
    public ResponseEntity<List<Seminar>> getHistory(
            @RequestParam String department,
            @RequestParam String email) {
        return ResponseEntity.ok(seminarService.getByDepartmentAndEmail(department, email));
    }
}
