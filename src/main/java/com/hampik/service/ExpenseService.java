package com.hampik.service;

import com.hampik.dto.CreateExpenseDto;
import com.hampik.dto.ExpenseDto;
import com.hampik.entity.Expense;
import com.hampik.enums.ExpenseCategory;
import com.hampik.entity.Trip;
import com.hampik.entity.User;
import com.hampik.repository.ExpenseRepository;
import com.hampik.repository.TripRepository;
import com.hampik.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, 
                         TripRepository tripRepository, 
                         UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public ExpenseDto saveExpense(Long tripId, CreateExpenseDto dto) {
        Trip trip = tripRepository.findById(tripId)
            .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
        
        User payer = userRepository.findById(dto.payerId())
            .orElseThrow(() -> new RuntimeException("Payer not found with id: " + dto.payerId()));
        
        List<User> partners = dto.partnerIds() != null 
            ? userRepository.findAllById(dto.partnerIds())
            : List.of();
        
        if (dto.partnerIds() != null && !dto.partnerIds().isEmpty() && partners.size() != dto.partnerIds().size()) {
            throw new RuntimeException("Some partner IDs are invalid");
        }

        Expense expense = new Expense();
        expense.setTrip(trip);
        expense.setTitle(dto.title());
        expense.setCategory(ExpenseCategory.valueOf(dto.category().toUpperCase()));
        expense.setAmount(dto.amount());
        expense.setPayer(payer);
        expense.setPartners(partners);
        expense.setSpentAt(dto.spentAt());
        expense.setBonus(dto.bonus());

        Expense savedExpense = expenseRepository.save(expense);
        return toDto(savedExpense);
    }

    @Transactional(readOnly = true)
    public ExpenseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        return toDto(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDto> getExpensesByTripId(Long tripId) {
        List<Expense> expenses = expenseRepository.findByTripId(tripId);
        return expenses.stream().map(this::toDto).toList();
    }

    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    private ExpenseDto toDto(Expense expense) {
        List<Long> partnerIds = expense.getPartners() != null 
            ? expense.getPartners().stream().map(User::getId).toList()
            : List.of();
        
        return new ExpenseDto(
            expense.getId(),
            expense.getTrip().getId(),
            expense.getTitle(),
            expense.getCategory().name(),
            expense.getAmount(),
            expense.getPayer().getId(),
            partnerIds,
            expense.getCreatedAt(),
            expense.getSpentAt(),
            expense.getBonus()
        );
    }
}
