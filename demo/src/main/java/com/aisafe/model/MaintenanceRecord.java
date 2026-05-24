package com.aisafe.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class MaintenanceRecord {

    @Id
    private String id;

    private String aircraftRegistration;
    private Long templateId;

    // 🧠 O atributo que o repositório exige para validar a query de soma!
    private Double expectedDurationHours;

    @ElementCollection
    private List<String> checklist;

    private String status; // SCHEDULED, IN_PROGRESS, COMPLETED
    private String completionNotes;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "period_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "period_end_date"))
    })
    private MaintenancePeriod period;

    public void complete(String notes) {
        this.status = "COMPLETED";
        this.completionNotes = notes;
    }
}