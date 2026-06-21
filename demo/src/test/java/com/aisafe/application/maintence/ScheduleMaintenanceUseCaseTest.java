package com.aisafe.application.maintence;

import com.aisafe.model.MaintenanceComponent;
import com.aisafe.model.MaintenancePeriod;
import com.aisafe.model.MaintenanceRecord;
import com.aisafe.repository.MaintenanceRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleMaintenanceUseCaseTest {

    @Mock
    private MaintenanceRecordRepository recordRepository;

    @InjectMocks
    private ScheduleMaintenanceUseCase scheduleMaintenanceUseCase;

    private MaintenanceRecord newRecord;

    @BeforeEach
    void setUp() {
        newRecord = new MaintenanceRecord();
        newRecord.setId("REC-115");
        newRecord.setAircraftRegistration("CS-TPA");
        newRecord.setExpectedDurationHours(8.5);
        newRecord.setStatus("SCHEDULED");

        newRecord.setComponent(MaintenanceComponent.ENGINE);
        newRecord.setChecklist(Arrays.asList("Inspect engine"));

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(8);
        newRecord.setPeriod(new MaintenancePeriod(start, end));
    }

    @Test
    void execute_SchedulesMaintenanceSuccessfully() {
        // Uso de any() para garantir que o Mockito não falha na comparação da instância
        when(recordRepository.save(any(MaintenanceRecord.class))).thenReturn(newRecord);

        MaintenanceRecord savedRecord = scheduleMaintenanceUseCase.execute(newRecord);

        assertNotNull(savedRecord, "O UseCase devolveu null.");
        assertEquals("CS-TPA", savedRecord.getAircraftRegistration());

        verify(recordRepository, times(1)).save(any(MaintenanceRecord.class));
    }
}