package com.frank.biblioteca.reservation.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.model.value_object.ReservationStatus;
import com.frank.biblioteca.reservation.domain.repository.ReservationRepository;
import com.frank.biblioteca.reservation.infrastructure.mapper.ReservationMapper;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;

    public ReservationRepositoryImpl(ReservationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Reservation entity) {
        jpaRepository.save(ReservationMapper.toEntity(entity));
    }

    @Override
    public Optional<Reservation> findById(UUID id) {
        return jpaRepository.findById(id).map(ReservationMapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return jpaRepository.findAll().stream()
            .map(ReservationMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).stream()
            .map(ReservationMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByBookId(UUID bookId) {
        return jpaRepository.findByBookId(bookId).stream()
            .map(ReservationMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByStatus(ReservationStatus status) {
        return jpaRepository.findByStatus(status.name()).stream()
            .map(ReservationMapper::toDomain)
            .collect(Collectors.toList());
    }
}
