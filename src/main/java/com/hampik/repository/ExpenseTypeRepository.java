package com.hampik.repository;

import com.hampik.entity.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

    List<ExpenseType> findByActive(boolean active);

    Optional<ExpenseType> findByName(String name);
}
