package com.hampik.controller;

import com.hampik.entity.ExpenseCategory;
import com.hampik.entity.ExpenseType;
import com.hampik.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-types")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @GetMapping
    public ResponseEntity<List<ExpenseType>> getAllExpenseTypes() {
        List<ExpenseType> expenseTypes = expenseTypeService.getAllExpenseTypes();
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseType> getExpenseTypeById(@PathVariable Integer id) {
        return expenseTypeService.getExpenseTypeById(id)
                .map(expenseType -> new ResponseEntity<>(expenseType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExpenseType>> getByCategory(@PathVariable ExpenseCategory category) {
        List<ExpenseType> expenseTypes = expenseTypeService.getByCategory(category);
        return new ResponseEntity<>(expenseTypes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ExpenseType> createExpenseType(@RequestBody ExpenseType expenseType) {
        try {
            ExpenseType created = expenseTypeService.createExpenseType(expenseType);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseType> updateExpenseType(@PathVariable Integer id, @RequestBody ExpenseType expenseType) {
        try {
            ExpenseType updated = expenseTypeService.updateExpenseType(id, expenseType);
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
