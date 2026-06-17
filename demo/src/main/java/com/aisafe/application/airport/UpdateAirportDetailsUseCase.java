package com.aisafe.application.airport;

import com.aisafe.core.exception.ResourceNotFoundException;
import com.aisafe.model.Airport;
import com.aisafe.model.ContactInfo;
import com.aisafe.model.IataCode;
import com.aisafe.model.OperatingHours;
import com.aisafe.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateAirportDetailsUseCase {

    @Autowired
    private AirportRepository airportRepository;

    public Airport execute(String iata, UpdateDetailsRequest request) {
        // 1. Fail-Fast: Validar o formato do IATA
        IataCode iataCode = new IataCode(iata);

        // 2. Ir buscar o Aeroporto à Base de Dados
        Airport airport = airportRepository.findById(iataCode)
                .orElseThrow(() -> new ResourceNotFoundException("Aeroporto não encontrado: " + iata));

        // 3. Atualizar os Contactos (se vierem no pedido)
        if (request.contactInfo != null) {
            airport.updateContactInfo(request.contactInfo);
        }

        // 4. Atualizar os Horários (se vierem no pedido)
        if (request.operatingHours != null) {
            airport.updateOperatingHours(request.operatingHours);
        }

        // 5. Guardar as alterações
        return airportRepository.save(airport);
    }

    // DTO: Define a estrutura do JSON que esperamos receber do Postman
    public static class UpdateDetailsRequest {
        public ContactInfo contactInfo;
        public OperatingHours operatingHours;
    }
}