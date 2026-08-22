package com.hampik.dto;

import com.hampik.entity.ExpenseCategory;

public record SaveExpenseTypeDto(
        Integer id,
        String title,
        String icon,
        ExpenseCategory category,
        Boolean hasBonus
) {
}
