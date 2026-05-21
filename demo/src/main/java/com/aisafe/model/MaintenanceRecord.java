package com.aisafe.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class MaintenanceRecord {
    @Id
    private String recordId = UUID.randomUUID().toString();

    @NotBlank(message = "A matrícula do avião é obrigatória.")
    private String aircraftRegistration;

    @NotBlank(message = "O ID do template é obrigatório.")
    private String templateId;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @FutureOrPresent(message = "A data de início não pode estar no passado.")
    private LocalDateTime startDate;

    @Positive(message = "A duração esperada deve ser positiva.")
    private double expectedDurationHours;

    @ElementCollection
    @NotEmpty(message = "A checklist não pode estar vazia.")
    private List<String> checklist = new ArrayList<>();

    private String maintenanceComponent = "GENERAL";
    private String status = "IN_PROGRESS";
    private LocalDateTime completionDate;
    private String completionNotes;

    @Version
    private Long version;

    public void complete(String notes) {
        if ("COMPLETED".equalsIgnoreCase(this.status)) {
            throw new IllegalStateException("Este registo de manutenção já está concluído.");
        }
        if (notes == null || notes.isBlank()) {
            throw new IllegalArgumentException("As notas de conclusão são obrigatórias.");
        }
        this.status = "COMPLETED";
        this.completionDate = LocalDateTime.now();
        this.completionNotes = notes;
    }
}
