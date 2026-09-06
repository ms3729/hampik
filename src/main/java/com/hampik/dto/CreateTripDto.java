package com.hampik.dto;

public record CreateTripDto(
        String type,
        String name,
        String startDate,
        String from,
        String to,
        String routeName,
        Integer totalKm,
        String location,
        String time
) {
}
