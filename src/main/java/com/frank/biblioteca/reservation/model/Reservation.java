package com.frank.biblioteca.reservation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.frank.biblioteca.reservation.model.value_object.ReservationStatus;

public class Reservation {
    private final UUID id;
    private final UUID userId;
    private final UUID bookId;
    private ReservationStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;

    public Reservation(UUID id, UUID userId, UUID bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.status = ReservationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusHours(12);
    }


    public void confirm(LocalDateTime today){
        if(status != ReservationStatus.PENDING)
            throw new IllegalArgumentException("the reservation must be pending,if you want to confirme");
        
        if(today.isAfter(expiresAt))
            throw new IllegalArgumentException("the reservation is experied");

        this.status = ReservationStatus.COMPLETED;

    }

    public void expire(LocalDateTime today){
        if(today.isBefore(expiresAt))
            throw new IllegalArgumentException("the reservation still pending");

        if(status != ReservationStatus.PENDING)
            throw new IllegalArgumentException("the reservation must be pending,if you want to expire");
        
        this.status = ReservationStatus.EXPIRED;
    }


    public void cancel() {

        if (status != ReservationStatus.PENDING) {
            throw new IllegalStateException(
                "Only pending reservations can be cancelled."
            );
        }
        this.status = ReservationStatus.CANCELLED;

    
}


}




