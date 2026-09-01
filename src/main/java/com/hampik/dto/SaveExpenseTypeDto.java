package com.hampik.dto;

import com.hampik.enums.ExpenseCategory;

public record SaveExpenseTypeDto(
        Integer id,
        String title,
        String icon,
        ExpenseCategory category,
        Boolean hasBonus
) {
}
