package com.dtao.booking_project.repo;

import com.dtao.booking_project.model.BookingRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRequestRepository extends MongoRepository<BookingRequest, String> {
    List<BookingRequest> findByEmail(String email);
    List<BookingRequest> findByDepartment(String department);

    // ✅ New
    List<BookingRequest> findByEmailAndDepartment(String email, String department);

    List<BookingRequest> findByEmailAndStatus(String email, String status);
    List<BookingRequest> findByEmailAndDateBetween(String email, String startDate, String endDate);
}

