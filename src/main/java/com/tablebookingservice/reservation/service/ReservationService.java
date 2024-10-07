package com.tablebookingservice.reservation.service;

import com.tablebookingservice.reservation.dto.CreateReservation;
import com.tablebookingservice.reservation.dto.ReservationDto;
import com.tablebookingservice.reservation.dto.UpdateArrival;
import com.tablebookingservice.reservation.dto.UpdateReservation;
import com.tablebookingservice.reservation.entity.ReservationEntity;

import java.util.List;

public interface ReservationService {

    ReservationDto createReservation(CreateReservation.Request request);
    ReservationDto updateReservation(Long reservationId, UpdateReservation.Request request);
    List<ReservationEntity> searchReservation(Long id);
    ReservationDto updateArrival(Long reservationId, UpdateArrival.Request request);
    ReservationDto cancelReservation(Long reservationId);
}
