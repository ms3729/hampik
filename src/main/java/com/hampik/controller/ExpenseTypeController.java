package com.hampik.controller;

import com.hampik.dto.ExpenseTypeDto;
import com.hampik.dto.SaveExpenseTypeDto;
import com.hampik.entity.ExpenseCategory;
import com.hampik.service.ExpenseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-types")
@AllArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @GetMapping
    public ResponseEntity<List<ExpenseTypeDto>> getAllExpenseTypes() {
        List<ExpenseTypeDto> expenseTypes = expenseTypeService.getAllExpenseTypes();
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseTypeDto> getExpenseTypeById(@PathVariable Integer id) {
        return expenseTypeService.getExpenseTypeById(id)
                .map(expenseType -> new ResponseEntity<>(expenseType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseTypeDto>> getByCategory(@PathVariable ExpenseCategory category) {
        List<ExpenseTypeDto> expenseTypes = expenseTypeService.getByCategory(category);
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseTypeDto> createExpenseType(@RequestBody SaveExpenseTypeDto dto) {
        try {
            ExpenseTypeDto created = expenseTypeService.createExpenseType(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseTypeDto> updateExpenseType(@PathVariable Integer id, @RequestBody SaveExpenseTypeDto dto) {
        try {
            ExpenseTypeDto updated = expenseTypeService.updateExpenseType(id, dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpenseType(@PathVariable Integer id) {
        try {
            expenseTypeService.deleteExpenseType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
