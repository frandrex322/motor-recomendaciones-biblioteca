package com.frank.biblioteca.reservation.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frank.biblioteca.reservation.infrastructure.entity.ReservationEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, UUID> {
    List<ReservationEntity> findByUserId(UUID userId);
    List<ReservationEntity> findByBookId(UUID bookId);
    List<ReservationEntity> findByStatus(String status);
}
