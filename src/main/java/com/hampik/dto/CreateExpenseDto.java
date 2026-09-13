package com.hampik.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record CreateExpenseDto(
    String title,
    String category,
    Double amount,
    Long payerId,
    List<Long> partnerIds,
    OffsetDateTime spentAt,
    Double bonus
) {}
