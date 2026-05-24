package com.aisafe.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MaintenancePeriodTest {

    @Test
    void us116_quandoDatasForemValidas_deveCriarPeriodoComSucesso() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(4);

        MaintenancePeriod period = new MaintenancePeriod(start, end);

        assertEquals(start, period.getStartDate());
        assertEquals(end, period.getEndDate());
    }

    @Test
    void us116_quandoDataFimForAnteriorADataInicio_deveLancarIllegalArgumentException() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusHours(2);

        assertThrows(IllegalArgumentException.class, () -> {
            new MaintenancePeriod(start, end);
        });
    }

    @Test
    void us115_deveInstanciarRegistoDeManutencaoComDadosBasicos() {
        MaintenanceRecord record = new MaintenanceRecord();
        record.setId("REC-123");
        record.setStatus("SCHEDULED");

        assertEquals("REC-123", record.getId());
        assertEquals("SCHEDULED", record.getStatus());
    }
}