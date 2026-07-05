package com.frank.biblioteca.reservation.application.service;

import java.util.UUID;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.repository.ReservationRepository;

public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));
        reservation.cancel();
        reservationRepository.save(reservation);
    }
}
