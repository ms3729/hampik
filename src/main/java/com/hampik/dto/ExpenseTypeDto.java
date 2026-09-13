package com.hampik.dto;

import com.hampik.enums.ExpenseCategory;

public record ExpenseTypeDto(
        Integer id,
        String title,
        String icon,
        String category,
        Boolean hasBonus
) {
}
