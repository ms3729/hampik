package com.hampik.repository;

import com.hampik.entity.ExpenseCategory;
import com.hampik.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {

    List<ExpenseType> findByCategory(ExpenseCategory category);
}
