package com.frank.biblioteca.reservation.domain.repository;

import java.util.List;
import java.util.UUID;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.model.value_object.ReservationStatus;
import com.frank.biblioteca.utils.RepositoryGeneric;

public interface ReservationRepository extends RepositoryGeneric<UUID, Reservation> {
    List<Reservation> findByUserId(UUID userId);
    List<Reservation> findByBookId(UUID bookId);
    List<Reservation> findByStatus(ReservationStatus status);
}
