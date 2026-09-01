package com.hampik.dto;

import com.hampik.enums.TripType;

import java.time.LocalDate;

public record CreateTripDto(
    TripType type,
    String name,
    LocalDate startDate,
    String from,
    String to,
    String routeName,
    Double totalKm,
    String location,
    String time
) {}
