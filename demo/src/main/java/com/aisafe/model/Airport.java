package com.aisafe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
public class Airport {

    @Id
    @Column(length = 3)
    private String iataCode; // Ex: LIS, OPO

    private String name;
    private String city;
    private String country;
    private String timezone;
    private String status; // OPERATIONAL, CLOSED, UNDER_MAINTENANCE
}