package com.aisafe.controller;

import com.aisafe.application.maintence.CloseMaintenanceRecordUseCase;
import com.aisafe.application.maintence.ScheduleMaintenanceUseCase;
import com.aisafe.model.MaintenanceRecord;
import com.aisafe.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(
        name = "Maintenance Records",
        description = "Gestão de Registos de Manutenção (US115, US116, US117, US119)"
)
@RestController
@RequestMapping("/api/maintenance-records")
public class MaintenanceRecordController {

    private final MaintenanceService maintenanceService;
    private final ScheduleMaintenanceUseCase scheduleMaintenanceUseCase;
    private final CloseMaintenanceRecordUseCase closeMaintenanceRecordUseCase;

    public MaintenanceRecordController(
            MaintenanceService maintenanceService,
            ScheduleMaintenanceUseCase scheduleMaintenanceUseCase,
            CloseMaintenanceRecordUseCase closeMaintenanceRecordUseCase
    ) {
        this.maintenanceService = maintenanceService;
        this.scheduleMaintenanceUseCase = scheduleMaintenanceUseCase;
        this.closeMaintenanceRecordUseCase = closeMaintenanceRecordUseCase;
    }

    @Operation(summary = "Criar Registo de Manutenção")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceRecord createRecord(@Valid @RequestBody MaintenanceRecord record) {
        return scheduleMaintenanceUseCase.execute(record);
    }

    @Operation(summary = "Listar Registos de Manutenção por Aeronave")
    @GetMapping("/aircraft/{registration}")
    public List<MaintenanceRecord> recordsForAircraft(@PathVariable String registration) {
        return maintenanceService.recordsForAircraft(registration);
    }

    @Operation(summary = "Ver Total de Horas de Manutenção de uma Aeronave")
    @GetMapping("/aircraft/{registration}/hours")
    public Map<String, Object> totalHoursForAircraft(@PathVariable String registration) {
        return Map.of(
                "aircraftRegistration", registration,
                "totalMaintenanceHours", maintenanceService.totalHoursForAircraft(registration)
        );
    }

    @Operation(summary = "Ver Total de Horas de Manutenção da Frota")
    @GetMapping("/fleet/hours")
    public Map<String, Object> totalHoursForFleet() {
        return Map.of(
                "totalMaintenanceHours",
                maintenanceService.totalHoursForFleet()
        );
    }

    @Operation(summary = "Completar Registo de Manutenção")
    @PatchMapping("/{recordId}/complete")
    public MaintenanceRecord completeRecord(
            @PathVariable String recordId,
            @Valid @RequestBody CompleteMaintenanceRequest request
    ) {
        return closeMaintenanceRecordUseCase.execute(
                recordId,
                request.getCompletionNotes()
        );
    }

    public static class CompleteMaintenanceRequest {

        @NotBlank
        private String completionNotes;

        public String getCompletionNotes() {
            return completionNotes;
        }

        public void setCompletionNotes(String completionNotes) {
            this.completionNotes = completionNotes;
        }
    }
}