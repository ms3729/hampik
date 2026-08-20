package com.hampik.repository;

import com.hampik.entity.ExpenseType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseTypeRepository extends MongoRepository<ExpenseType, String> {

    List<ExpenseType> findByActive(boolean active);

    ExpenseType findByName(String name);
}
