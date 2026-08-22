package com.hampik.service;

import com.hampik.dto.ExpenseTypeDto;
import com.hampik.dto.SaveExpenseTypeDto;
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

    public List<ExpenseTypeDto> getAllExpenseTypes() {
        return expenseTypeRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public Optional<ExpenseTypeDto> getExpenseTypeById(Integer id) {
        return expenseTypeRepository.findById(id)
                .map(this::toDto);
    }

    public ExpenseTypeDto createExpenseType(SaveExpenseTypeDto dto) {
        if (expenseTypeRepository.findAll().stream()
                .anyMatch(e -> e.getTitle().equals(dto.title()))) {
            throw new RuntimeException("ExpenseType with title '" + dto.title() + "' already exists");
        }
        ExpenseType expenseType = toEntity(dto);
        expenseType.setId(null); // Ensure ID is null for new entities
        ExpenseType saved = expenseTypeRepository.save(expenseType);
        return toDto(saved);
    }

    public ExpenseTypeDto updateExpenseType(Integer id, SaveExpenseTypeDto dto) {
        return expenseTypeRepository.findById(id)
                .map(existing -> {
                    if (!existing.getTitle().equals(dto.title())) {
                        if (expenseTypeRepository.findAll().stream()
                                .anyMatch(e -> e.getTitle().equals(dto.title()))) {
                            throw new RuntimeException("ExpenseType with title '" + dto.title() + "' already exists");
                        }
                    }
                    existing.setTitle(dto.title());
                    existing.setIcon(dto.icon());
                    existing.setCategory(dto.category());
                    existing.setHasBonus(dto.hasBonus());
                    return toDto(expenseTypeRepository.save(existing));
                })
                .orElseThrow(() -> new RuntimeException("ExpenseType not found with id: " + id));
    }

    public void deleteExpenseType(Integer id) {
        if (!expenseTypeRepository.existsById(id)) {
            throw new RuntimeException("ExpenseType not found with id: " + id);
        }
        expenseTypeRepository.deleteById(id);
    }

    public List<ExpenseTypeDto> getByCategory(ExpenseCategory category) {
        return expenseTypeRepository.findByCategory(category).stream()
                .map(this::toDto)
                .toList();
    }

    private ExpenseTypeDto toDto(ExpenseType entity) {
        return new ExpenseTypeDto(
                entity.getId(),
                entity.getTitle(),
                entity.getIcon(),
                entity.getCategory(),
                entity.getHasBonus()
        );
    }

    private ExpenseType toEntity(SaveExpenseTypeDto dto) {
        ExpenseType entity = new ExpenseType();
        entity.setTitle(dto.title());
        entity.setIcon(dto.icon());
        entity.setCategory(dto.category());
        entity.setHasBonus(dto.hasBonus());
        return entity;
    }
}
