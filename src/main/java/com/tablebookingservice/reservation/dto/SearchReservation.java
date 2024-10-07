package com.tablebookingservice.reservation.dto;

import com.tablebookingservice.reservation.entity.ReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchReservation {
    private List<ReservationDto> reservations;

    public static SearchReservation of(List<ReservationEntity> reservations) {
        return new SearchReservation(reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList()));
    }
}
