package com.hampik.dto;

import com.hampik.enums.TripStatus;
import com.hampik.enums.TripType;

import java.time.LocalDate;

public record TripSummaryDto(
    Long id,
    TripType type,
    String name,
    LocalDate startDate,
    TripStatus status,
    String from,
    String to,
    String routeName,
    Double totalKm,
    String location,
    String time
) {}
