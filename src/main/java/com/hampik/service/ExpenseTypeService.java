package com.hampik.service;

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

    public Optional<ExpenseType> getExpenseTypeById(Long id) {
        return expenseTypeRepository.findById(id);
    }

    public ExpenseType createExpenseType(ExpenseType expenseType) {
        if (expenseTypeRepository.findByName(expenseType.getName()).isPresent()) {
            throw new RuntimeException("ExpenseType with name '" + expenseType.getName() + "' already exists");
        }
        return expenseTypeRepository.save(expenseType);
    }

    public ExpenseType updateExpenseType(Long id, ExpenseType expenseType) {
        return expenseTypeRepository.findById(id)
                .map(existing -> {
                    if (!existing.getName().equals(expenseType.getName())) {
                        if (expenseTypeRepository.findByName(expenseType.getName()).isPresent()) {
                            throw new RuntimeException("ExpenseType with name '" + expenseType.getName() + "' already exists");
                        }
                    }
                    existing.setName(expenseType.getName());
                    existing.setDescription(expenseType.getDescription());
                    existing.setActive(expenseType.isActive());
                    return expenseTypeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ExpenseType not found with id: " + id));
    }

    public void deleteExpenseType(Long id) {
        if (!expenseTypeRepository.existsById(id)) {
            throw new RuntimeException("ExpenseType not found with id: " + id);
        }
        expenseTypeRepository.deleteById(id);
    }

    public List<ExpenseType> getActiveExpenseTypes() {
        return expenseTypeRepository.findByActive(true);
    }

    public Optional<ExpenseType> getByName(String name) {
        return expenseTypeRepository.findByName(name);
    }
}
