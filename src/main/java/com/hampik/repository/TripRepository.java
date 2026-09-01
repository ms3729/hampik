package com.hampik.repository;

import com.hampik.entity.Trip;
import com.hampik.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByStatus(TripStatus status);
    Optional<Trip> findFirstByStatusOrderByStartDateDesc(TripStatus status);
}
