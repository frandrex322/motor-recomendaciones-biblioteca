package com.frank.biblioteca.reservation.application.service;

import java.util.UUID;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.repository.ReservationRepository;

public class CreateReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CreateReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(UUID userId, UUID bookId) {
        Reservation reservation = new Reservation(UUID.randomUUID(), userId, bookId);
        reservationRepository.save(reservation);
    }
}
