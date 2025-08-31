package com.dtao.booking_project.service;

import com.dtao.booking_project.model.Seminar;
import com.dtao.booking_project.repo.SeminarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeminarService {

    @Autowired
    private SeminarRepository seminarRepository;

    // ✅ Add with slot conflict check
    public Seminar addSeminar(Seminar seminar) {
        List<Seminar> existing = seminarRepository.findByHallNameAndDateAndSlot(
                seminar.getHallName(), seminar.getDate(), seminar.getSlot()
        );
        if (!existing.isEmpty()) {
            throw new RuntimeException("Slot already booked for "
                    + seminar.getHallName() + " on " + seminar.getDate()
                    + " (" + seminar.getSlot() + ")");
        }
        return seminarRepository.save(seminar);
    }

    public List<Seminar> getAllSeminars() {
        return seminarRepository.findAll();
    }

    public List<Seminar> getSeminarsByDate(String date) {
        return seminarRepository.findByDate(date);
    }

    // ✅ Get by hall & date
    public List<Seminar> getByHallAndDate(String hallName, String date) {
        return seminarRepository.findByDateAndHallName(date, hallName);
    }

    // ✅ Full update
    public Seminar updateSeminar(String id, Seminar updatedSeminar) {
        return seminarRepository.findById(id).map(seminar -> {
            seminar.setHallName(updatedSeminar.getHallName());
            seminar.setDate(updatedSeminar.getDate());
            seminar.setSlot(updatedSeminar.getSlot());
            seminar.setSlotTitle(updatedSeminar.getSlotTitle());
            seminar.setBookingName(updatedSeminar.getBookingName());
            seminar.setEmail(updatedSeminar.getEmail());
            seminar.setDepartment(updatedSeminar.getDepartment());
            seminar.setPhone(updatedSeminar.getPhone());
            seminar.setStartTime(updatedSeminar.getStartTime());
            seminar.setEndTime(updatedSeminar.getEndTime());
            seminar.setStatus(updatedSeminar.getStatus());
            return seminarRepository.save(seminar);
        }).orElse(null);
    }

    public void deleteSeminar(String id) {
        seminarRepository.deleteById(id);
    }
}
