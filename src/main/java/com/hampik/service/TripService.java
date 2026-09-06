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
        return tripRepository.findFirstByStatusOrderByStartDateDesc(TripStatus.active)
                .map(this::toDto)
                .orElse(null);
    }

    public TripSummaryDto createTrip(CreateTripDto dto) {
        // Deactivate any existing active trips
        tripRepository.findByStatus(TripStatus.active).forEach(trip -> {
            trip.setStatus(TripStatus.finished);
            trip.setFinishedAt(OffsetDateTime.now());
            tripRepository.save(trip);
        });

        Trip trip = new Trip();
        trip.setType(TripType.valueOf(dto.type()));
        trip.setTitle(dto.name());
        trip.setStartDate(LocalDate.parse(dto.startDate()));
        trip.setOrigin(dto.from());
        trip.setDestination(dto.to());
        trip.setRouteName(dto.routeName());
        trip.setLocation_type(dto.location());
        if(trip.getType().equals(TripType.trip)) {
            trip.setTotalKm(dto.totalKm());
        }
        trip.setTim(dto.time());
        trip.setStatus(TripStatus.active);

        tripRepository.save(trip);
        return toDto(trip);
    }

    public TripSummaryDto activateTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));

        // Deactivate all other trips
        tripRepository.findByStatus(TripStatus.active).forEach(t -> {
            if (t.getId() != id) {
                t.setStatus(TripStatus.finished);
                t.setFinishedAt(OffsetDateTime.now());
                tripRepository.save(t);
            }
        });

        trip.setStatus(TripStatus.active);
        trip.setFinishedAt(null);
        tripRepository.save(trip);
        return toDto(trip);
    }

    public TripSummaryDto finishTrip(Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + id));

        trip.setStatus(TripStatus.finished);
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
                trip.getTitle(),
                trip.getStartDate().toString(),
                trip.getStatus().name(),
                trip.getOrigin(),
                trip.getDestination(),
                trip.getRouteName(),
                trip.getTotalKm(),
                trip.getLocation_type(),
                trip.getTim()
        );
    }
}
