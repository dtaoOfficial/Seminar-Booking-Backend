package com.dtao.booking_project.service;

import com.dtao.booking_project.model.BookingRequest;
import com.dtao.booking_project.model.Seminar;
import com.dtao.booking_project.repo.BookingRequestRepository;
import com.dtao.booking_project.repo.SeminarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Service
public class BookingRequestService {

    @Autowired
    private BookingRequestRepository bookingRequestRepository;

    @Autowired
    private SeminarRepository seminarRepository;

    // --- CRUD / basic ---
    public BookingRequest createRequest(BookingRequest request) {
        request.setStatus("PENDING");
        return bookingRequestRepository.save(request);
    }

    public List<BookingRequest> getAll() {
        return bookingRequestRepository.findAll();
    }

    public Optional<BookingRequest> getById(String id) {
        return bookingRequestRepository.findById(id);
    }

    public void delete(String id) {
        bookingRequestRepository.deleteById(id);
    }

    public List<BookingRequest> getByEmail(String email) {
        return bookingRequestRepository.findByEmail(email);
    }

    public List<BookingRequest> getByDepartment(String department) {
        return bookingRequestRepository.findByDepartment(department);
    }

    // --- Search (filters are optional) ---
    public List<BookingRequest> search(String department, String hall, String date,
                                       String slot, String status, String sort) {

        List<BookingRequest> list = bookingRequestRepository.findAll();

        List<BookingRequest> filtered = list.stream()
                .filter(r -> department == null || department.isBlank() ||
                        equalsIgnoreCaseSafe(r.getDepartment(), department))
                .filter(r -> hall == null || hall.isBlank() ||
                        equalsIgnoreCaseSafe(r.getHallName(), hall))
                .filter(r -> date == null || date.isBlank() ||
                        equalsIgnoreCaseSafe(r.getDate(), date))
                .filter(r -> slot == null || slot.isBlank() ||
                        (r.getSlot() != null && r.getSlot().toLowerCase().contains(slot.toLowerCase())))
                .filter(r -> status == null || status.isBlank() ||
                        equalsIgnoreCaseSafe(r.getStatus(), status))
                .collect(Collectors.toList());

        // sort: latest (default) or oldest
        if ("oldest".equalsIgnoreCase(sort)) {
            filtered.sort(Comparator.comparing(BookingRequest::getId));
        } else {
            filtered.sort(Comparator.comparing(BookingRequest::getId).reversed());
        }
        return filtered;
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        return (a == null && b == null) ||
                (a != null && b != null && a.equalsIgnoreCase(b));
    }

    // --- Approve / Reject ---

    /** Approve request -> check conflicts -> create Seminar -> mark request APPROVED */
    public BookingRequest approve(String requestId) {
        BookingRequest req = bookingRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Request not found"));

        if (!"PENDING".equalsIgnoreCase(req.getStatus())) {
            throw new ResponseStatusException(CONFLICT, "Only PENDING requests can be approved");
        }

        // Conflict check for the same hall & date
        List<Seminar> sameDay = seminarRepository.findByDateAndHallName(req.getDate(), req.getHallName());
        if (conflicts(sameDay, req.getSlot())) {
            throw new ResponseStatusException(CONFLICT, "Slot already booked for this hall & date");
        }

        // Create Seminar (confirmed booking)
        // inside approve(String requestId)
        Seminar seminar = new Seminar();
        seminar.setHallName(req.getHallName());
        seminar.setSlot(req.getSlot());
        seminar.setSlotTitle(req.getSlotTitle());
        seminar.setBookingName(req.getBookingName());
        seminar.setEmail(req.getEmail());   // ✅ now stored
        seminar.setDepartment(req.getDepartment());
        seminar.setPhone(req.getPhone());
        seminar.setDate(req.getDate());
        seminar.setStartTime(req.getStartTime());
        seminar.setEndTime(req.getEndTime());
        seminar.setStatus("APPROVED");

        seminarRepository.save(seminar);


        // Update request status
        req.setStatus("APPROVED");
        return bookingRequestRepository.save(req);
    }

    /** Reject request -> mark REJECTED (no seminar created) */
    public BookingRequest reject(String requestId) {
        BookingRequest req = bookingRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Request not found"));

        if (!"PENDING".equalsIgnoreCase(req.getStatus())) {
            throw new ResponseStatusException(CONFLICT, "Only PENDING requests can be rejected");
        }

        req.setStatus("REJECTED");
        return bookingRequestRepository.save(req);
    }

    // --- conflict logic (same as UI rules) ---
    private boolean conflicts(List<Seminar> existing, String newSlot) {
        boolean hasFull = existing.stream().anyMatch(s -> containsIgnoreCase(s.getSlot(), "full"));
        boolean hasMorning = existing.stream().anyMatch(s -> containsIgnoreCase(s.getSlot(), "morning"));
        boolean hasAfternoon = existing.stream().anyMatch(s -> containsIgnoreCase(s.getSlot(), "afternoon"));

        String ns = (newSlot == null) ? "" : newSlot.toLowerCase();

        if (hasFull) return true;
        if (ns.contains("full")) {
            return hasMorning || hasAfternoon;
        }
        if (ns.contains("morning")) return hasMorning;
        if (ns.contains("afternoon")) return hasAfternoon;
        return false;
    }

    private boolean containsIgnoreCase(String s, String sub) {
        return s != null && sub != null && s.toLowerCase().contains(sub.toLowerCase());
    }

    public List<BookingRequest> getByEmailAndStatus(String email, String status) {
        return bookingRequestRepository.findByEmailAndStatus(email, status);
    }

    public List<BookingRequest> getByEmailAndDateRange(String email, String startDate, String endDate) {
        return bookingRequestRepository.findByEmailAndDateBetween(email, startDate, endDate);
    }

    public List<BookingRequest> getByEmailAndDepartment(String email, String department) {
        return bookingRequestRepository.findByEmailAndDepartment(email, department);
    }

}
