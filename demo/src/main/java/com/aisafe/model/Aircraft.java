package com.aisafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Aircraft {

    @Id
    private String registrationNumber;

    private String modelId;
    private LocalDate manufacturingDate;
    private String status;

    @Version
    private Long version;
}