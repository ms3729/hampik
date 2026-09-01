package com.hampik.service;

import com.hampik.dto.CreateTripDto;
import com.hampik.dto.TripSummaryDto;
import com.hampik.entity.Trip;
import com.hampik.enums.TripStatus;
import com.hampik.enums.TripType;
import com.hampik.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {

    private final TripRepository tripRepository;

    @Transactional(readOnly = true)
    public List<TripSummaryDto> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TripSummaryDto getActiveTrip() {
        return tripRepository.findFirstByStatusOrderByStartDateDesc(TripStatus.ACTIVE)
                .map(this::toDto)
                .orElse(null);
    }

    public TripSummaryDto createTrip(CreateTripDto dto) {
        // Deactivate any existing active trips
        tripRepository.findByStatus(TripStatus.ACTIVE).forEach(trip -> {
            trip.setStatus(TripStatus.FINISHED);
            trip.setFinishedAt(OffsetDateTime.now());
            tripRepository.save(trip);
        });

        Trip trip = Trip.builder()
                .type(TripType.valueOf(dto.type()))
                .name(dto.name())
                .startDate(LocalDate.parse(dto.startDate()))
                .from(dto.from())
                .to(dto.to())
                .routeName(dto.routeName())
                .totalKm(dto.totalKm())
                .location(dto.location())
                .time(dto.time())
                .status(TripStatus.ACTIVE)
                .build();

        tripRepository.save(trip);
        return toDto(trip);
    }

    public TripSummaryDto activateTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));

        // Deactivate all other trips
        tripRepository.findByStatus(TripStatus.ACTIVE).forEach(t -> {
            if (!t.getId().equals(id)) {
                t.setStatus(TripStatus.FINISHED);
                t.setFinishedAt(OffsetDateTime.now());
                tripRepository.save(t);
            }
        });

        trip.setStatus(TripStatus.ACTIVE);
        trip.setFinishedAt(null);
        tripRepository.save(trip);
        return toDto(trip);
    }

    public TripSummaryDto finishTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));

        trip.setStatus(TripStatus.FINISHED);
        trip.setFinishedAt(OffsetDateTime.now());
        tripRepository.save(trip);
        return toDto(trip);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    private TripSummaryDto toDto(Trip trip) {
        return new TripSummaryDto(
                trip.getId(),
                trip.getType().name(),
                trip.getName(),
                trip.getStartDate().toString(),
                trip.getStatus().name(),
                trip.getFrom(),
                trip.getTo(),
                trip.getRouteName(),
                trip.getTotalKm(),
                trip.getLocation(),
                trip.getTime()
        );
    }
}
