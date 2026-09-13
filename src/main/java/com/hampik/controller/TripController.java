package com.hampik.controller;

import com.hampik.dto.CreateTripDto;
import com.hampik.dto.TripSummaryDto;
import com.hampik.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TripController {

    private final TripService tripService;

    @GetMapping
    public ResponseEntity<List<TripSummaryDto>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/active")
    public ResponseEntity<TripSummaryDto> getActiveTrip() {
        TripSummaryDto activeTrip = tripService.getActiveTrip();
        if (activeTrip == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activeTrip);
    }

    @PostMapping
    public ResponseEntity<TripSummaryDto> createTrip(@RequestBody CreateTripDto dto) {
        return ResponseEntity.ok(tripService.createTrip(dto));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<TripSummaryDto> activateTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.activateTrip(id));
    }

    @PostMapping("/{id}/finish")
    public ResponseEntity<TripSummaryDto> finishTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.finishTrip(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
