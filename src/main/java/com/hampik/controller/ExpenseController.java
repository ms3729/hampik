package com.hampik.controller;

import com.hampik.dto.CreateExpenseDto;
import com.hampik.dto.ExpenseDto;
import com.hampik.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        ExpenseDto expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getExpensesByTripId(@RequestParam Long tripId) {
        List<ExpenseDto> expenses = expenseService.getExpensesByTripId(tripId);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> saveExpense(@RequestParam Long tripId, 
                                                  @RequestBody CreateExpenseDto dto) {
        ExpenseDto savedExpense = expenseService.saveExpense(tripId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
