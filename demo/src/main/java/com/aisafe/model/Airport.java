package com.aisafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data // Isto cria os Getters e Setters automaticamente graças ao Lombok
public class Airport {

    @Id
    @Column(length = 3)
    private String iataCode; // Ex: LIS, OPO

    private String name;
    private String city;
    private String country;
    private String timezone;
    private String status; // OPERATIONAL, CLOSED, UNDER_MAINTENANCE

    // Se o teu colega precisar de mais campos (pistas, etc), ele adiciona aqui depois
}