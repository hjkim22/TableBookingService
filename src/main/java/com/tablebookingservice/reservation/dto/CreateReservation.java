package com.tablebookingservice.reservation.dto;

import com.tablebookingservice.reservation.type.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservation {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        @NotNull
        private Long userId;

        @NotNull
        private Long storeId;

        private LocalDate reservationDate;
        private LocalTime reservationTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String username;
        private String userPhoneNumber;
        private String storeName;
        private ReservationStatus reservationStatus;

        private LocalDate reservationDate;
        private LocalTime reservationTime;

        public static Response of(ReservationDto reservation) {
            return Response.builder()
                    .username(reservation.getUsername())
                    .userPhoneNumber(reservation.getUserPhoneNumber())
                    .storeName(reservation.getStoreName())
                    .reservationStatus(reservation.getReservationStatus())
                    .reservationDate(reservation.getReservationDate())
                    .reservationTime(reservation.getReservationTime())
                    .build();
        }
    }
}
