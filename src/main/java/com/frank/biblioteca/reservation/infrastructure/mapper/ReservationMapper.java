package com.frank.biblioteca.reservation.infrastructure.mapper;

import java.time.LocalDateTime;

import com.frank.biblioteca.reservation.domain.model.Reservation;
import com.frank.biblioteca.reservation.infrastructure.entity.ReservationEntity;

public class ReservationMapper {

    public static ReservationEntity toEntity(Reservation domain) {
        return new ReservationEntity(domain.getId(), domain.getUserId(), domain.getBookId(),
            domain.getStatus().name(), domain.getCreatedAt(), domain.getExpiresAt());
    }

    public static Reservation toDomain(ReservationEntity entity) {
        Reservation reservation = new Reservation(entity.getId(), entity.getUserId(), entity.getBookId());
        if ("COMPLETED".equals(entity.getStatus())) reservation.confirm(LocalDateTime.now());
        if ("CANCELLED".equals(entity.getStatus())) reservation.cancel();
        if ("EXPIRED".equals(entity.getStatus())) reservation.expire(LocalDateTime.now());
        return reservation;
    }
}
