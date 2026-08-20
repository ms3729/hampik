package com.hampik.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "expense_types")
public class ExpenseType {

    @Id
    private String id;

    private String name;

    private String description;

    private boolean active;
}
