package com.frank.biblioteca.loan.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.loan.domain.model.Loan;
import com.frank.biblioteca.loan.domain.repository.LoanRepository;
import com.frank.biblioteca.loan.infrastructure.mapper.LoanMapper;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    private final LoanJpaRepository jpaRepository;

    public LoanRepositoryImpl(LoanJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Loan entity) {
        jpaRepository.save(LoanMapper.toEntity(entity));
    }

    @Override
    public Optional<Loan> findById(UUID id) {
        return jpaRepository.findById(id).map(LoanMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Loan> findAll() {
        return jpaRepository.findAll().stream()
            .map(LoanMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(LoanMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByBookId(UUID bookId) {
        return jpaRepository.findByBookId(bookId).stream()
            .map(LoanMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
            .map(LoanMapper::toDomain)
            .collect(Collectors.toList());
    }
}
