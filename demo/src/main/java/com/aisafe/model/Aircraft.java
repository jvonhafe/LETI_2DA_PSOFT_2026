package com.aisafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Aircraft {

    @Id
    @NotBlank(message = "A matrícula da aeronave é obrigatória.")

    @Pattern(regexp = "^[A-Z0-9]{1,5}-[A-Z0-9]{1,5}$|^[A-Z0-9]{3,7}$",
            message = "O formato da matrícula é inválido. Exemplo correto: CS-TTO")
    private String registrationNumber;

    @NotBlank(message = "O ID do modelo é obrigatório.")
    private String modelId;

    @NotNull(message = "A data de fabrico é obrigatória.")
    @PastOrPresent(message = "A data de fabrico não pode estar no futuro.")
    private LocalDate manufacturingDate;

    @NotBlank(message = "O estado da aeronave é obrigatório.")
    @Pattern(regexp = "^(ACTIVE|INACTIVE|UNDER_MAINTENANCE)$",
            message = "O estado deve ser obrigatoriamente: ACTIVE, INACTIVE ou UNDER_MAINTENANCE")
    private String status;

    @Version
    private Long version;
}