package com.aisafe.controller;

import com.aisafe.service.MaintenanceService;
import com.aisafe.model.MaintenanceRecord;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance-records")
public class MaintenanceRecordController {

    private final MaintenanceService maintenanceService;

    public MaintenanceRecordController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceRecord createRecord(@Valid @RequestBody MaintenanceRecord record) {
        return maintenanceService.createRecord(record);
    }

    @GetMapping("/aircraft/{registration}")
    public List<MaintenanceRecord> recordsForAircraft(@PathVariable String registration) {
        return maintenanceService.recordsForAircraft(registration);
    }

    @GetMapping("/aircraft/{registration}/hours")
    public Map<String, Object> totalHoursForAircraft(@PathVariable String registration) {
        return Map.of(
                "aircraftRegistration", registration,
                "totalMaintenanceHours", maintenanceService.totalHoursForAircraft(registration)
        );
    }

    @GetMapping("/fleet/hours")
    public Map<String, Object> totalHoursForFleet() {
        return Map.of(
                "totalMaintenanceHours",
                maintenanceService.totalHoursForFleet()
        );
    }

    @PatchMapping("/{recordId}/complete")
    public MaintenanceRecord completeRecord(
            @PathVariable String recordId,
            @Valid @RequestBody CompleteMaintenanceRequest request
    ) {
        return maintenanceService.completeRecord(
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