package com.hampik.service;

import com.hampik.entity.ExpenseCategory;
import com.hampik.entity.ExpenseType;
import com.hampik.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    public List<ExpenseType> getAllExpenseTypes() {
        return expenseTypeRepository.findAll();
    }

    public Optional<ExpenseType> getExpenseTypeById(Integer id) {
        return expenseTypeRepository.findById(id);
    }

    public ExpenseType createExpenseType(ExpenseType expenseType) {
        if (expenseTypeRepository.findAll().stream()
                .anyMatch(e -> e.getTitle().equals(expenseType.getTitle()))) {
            throw new RuntimeException("ExpenseType with title '" + expenseType.getTitle() + "' already exists");
        }
        return expenseTypeRepository.save(expenseType);
    }

    public ExpenseType updateExpenseType(Integer id, ExpenseType expenseType) {
        return expenseTypeRepository.findById(id)
                .map(existing -> {
                    if (!existing.getTitle().equals(expenseType.getTitle())) {
                        if (expenseTypeRepository.findAll().stream()
                                .anyMatch(e -> e.getTitle().equals(expenseType.getTitle()))) {
                            throw new RuntimeException("ExpenseType with title '" + expenseType.getTitle() + "' already exists");
                        }
                    }
                    existing.setTitle(expenseType.getTitle());
                    existing.setIcon(expenseType.getIcon());
                    existing.setCategory(expenseType.getCategory());
                    existing.setHasBonus(expenseType.getHasBonus());
                    return expenseTypeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ExpenseType not found with id: " + id));
    }

    public void deleteExpenseType(Integer id) {
        if (!expenseTypeRepository.existsById(id)) {
            throw new RuntimeException("ExpenseType not found with id: " + id);
        }
        expenseTypeRepository.deleteById(id);
    }

    public List<ExpenseType> getByCategory(ExpenseCategory category) {
        return expenseTypeRepository.findByCategory(category);
    }
}
