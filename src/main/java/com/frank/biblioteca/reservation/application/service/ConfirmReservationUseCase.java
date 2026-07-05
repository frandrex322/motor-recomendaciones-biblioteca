package com.frank.biblioteca.reservation.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.repository.ReservationRepository;

public class ConfirmReservationUseCase {
    private final ReservationRepository reservationRepository;

    public ConfirmReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));
        reservation.confirm(LocalDateTime.now());
        reservationRepository.save(reservation);
    }
}
