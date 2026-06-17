<<<<<<< HEAD
package com.aisafe.application.airport;

import com.aisafe.core.exception.DuplicateResourceException;
=======
package com.aisafe.application.airport; // ou com.aisafe.usecase.airport

>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
import com.aisafe.model.Airport;
import com.aisafe.repository.AirportRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisterAirportUseCase {

    private final AirportRepository airportRepository;

    public RegisterAirportUseCase(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport execute(Airport airport) {
        if (airportRepository.existsById(airport.getIataCode())) {
<<<<<<< HEAD
            throw new DuplicateResourceException("Já existe um aeroporto com IATA: " + airport.getIataCode().getCode());
=======
            throw new IllegalArgumentException("Já existe um aeroporto com IATA: " + airport.getIataCode().getCode());
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
        }
        return airportRepository.save(airport);
    }
}