package com.dtao.booking_project.repo;

import com.dtao.booking_project.model.Seminar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// SeminarRepository.java
@Repository
public interface SeminarRepository extends MongoRepository<Seminar, String> {
    List<Seminar> findByHallNameAndDateAndSlot(String hallName, String date, String slot);
    List<Seminar> findByDate(String date);
    List<Seminar> findByDateAndHallName(String date, String hallName);

    // ✅ New
    List<Seminar> findByDepartmentAndEmail(String department, String email);
}


