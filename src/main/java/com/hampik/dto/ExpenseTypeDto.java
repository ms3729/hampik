package com.hampik.dto;


public record ExpenseTypeDto(
        Integer id,
        String title,
        String icon,
        String category,
        Boolean hasBonus
) {
}
