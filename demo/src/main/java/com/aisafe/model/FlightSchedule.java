package com.aisafe.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Data
@NoArgsConstructor
public class FlightSchedule {

    private LocalDateTime departureLDT;
    private LocalDateTime arrivalLDT;

    public FlightSchedule(LocalDateTime departureLDT, LocalDateTime arrivalLDT) {
        if (arrivalLDT.isBefore(departureLDT)) {
            throw new IllegalArgumentException("A data de chegada não pode ser anterior à data de partida.");
        }
        this.departureLDT = departureLDT;
        this.arrivalLDT = arrivalLDT;
    }
}