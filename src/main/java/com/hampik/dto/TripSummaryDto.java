package com.hampik.dto;

public record TripSummaryDto(
        Long id,
        String type,
        String name,
        String startDate,
        String status,
        String from,
        String to,
        String routeName,
        int totalKm,
        String location,
        String time
) {
}
