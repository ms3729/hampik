package com.hampik.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record ExpenseDto(
    Long id,
    Long tripId,
    String title,
    String category,
    Double amount,
    Long payerId,
    List<Long> partnerIds,
    OffsetDateTime createdAt,
    OffsetDateTime spentAt,
    Double bonus
) {}
