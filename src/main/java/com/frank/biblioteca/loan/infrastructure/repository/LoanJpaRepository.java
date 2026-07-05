package com.frank.biblioteca.loan.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.loan.infrastructure.entity.LoanEntity;

public interface LoanJpaRepository extends JpaRepository<LoanEntity, UUID> {
    List<LoanEntity> findByUserId(UUID userId);
    List<LoanEntity> findByBookId(UUID bookId);
    List<LoanEntity> findByStatus(String status);
}
