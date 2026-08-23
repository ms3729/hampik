package com.hampik.dto;

public record SaveExpenseTypeDto(
        Integer id,
        String title,
        String icon,
        String category,
        Boolean hasBonus
) {
}
