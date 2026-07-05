package com.frank.biblioteca.reservation.infrastructure.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.domain.repository.ReservationRepository;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody CreateReservationRequest request) {
        Reservation reservation = new Reservation(UUID.randomUUID(), request.userId, request.bookId);
        reservationRepository.save(reservation);
        return reservation;
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> findByUser(@PathVariable UUID userId) {
        return reservationRepository.findByUserId(userId);
    }

    @PatchMapping("/{id}/confirm")
    public Reservation confirm(@PathVariable UUID id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.confirm(java.time.LocalDateTime.now());
        reservationRepository.save(reservation);
        return reservation;
    }

    @PatchMapping("/{id}/cancel")
    public Reservation cancel(@PathVariable UUID id) {
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.cancel();
        reservationRepository.save(reservation);
        return reservation;
    }

    record CreateReservationRequest(UUID userId, UUID bookId) {}
}
