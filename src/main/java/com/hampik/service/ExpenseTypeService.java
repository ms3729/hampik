package com.hampik.service;

import com.hampik.entity.ExpenseType;
import com.hampik.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public Optional<ExpenseType> getExpenseTypeById(String id) {
        return expenseTypeRepository.findById(id);
    }

    public ExpenseType createExpenseType(ExpenseType expenseType) {
        return expenseTypeRepository.save(expenseType);
    }

    public ExpenseType updateExpenseType(String id, ExpenseType expenseType) {
        if (expenseTypeRepository.existsById(id)) {
            expenseType.setId(id);
            return expenseTypeRepository.save(expenseType);
        }
        throw new RuntimeException("ExpenseType not found with id: " + id);
    }

    public void deleteExpenseType(String id) {
        expenseTypeRepository.deleteById(id);
    }

    public List<ExpenseType> getActiveExpenseTypes() {
        return expenseTypeRepository.findByActive(true);
    }

    public ExpenseType getByName(String name) {
        return expenseTypeRepository.findByName(name);
    }
}
