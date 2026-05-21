package com.aisafe.controller;

import com.aisafe.service.MaintenanceService;
import com.aisafe.model.MaintenanceTemplate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance-templates")
public class MaintenanceTemplateController {
    private final MaintenanceService maintenanceService;

    public MaintenanceTemplateController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceTemplate createTemplate(@Valid @RequestBody MaintenanceTemplate template) {
        return maintenanceService.createTemplate(template);
    }
}
