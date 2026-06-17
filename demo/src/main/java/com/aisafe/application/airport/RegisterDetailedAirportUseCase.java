package com.aisafe.application.airport;

<<<<<<< HEAD
import com.aisafe.core.exception.DuplicateResourceException;
=======
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
import com.aisafe.model.Airport;
import com.aisafe.model.Facility;
import com.aisafe.model.IataCode;
import com.aisafe.model.MediaImage;
import com.aisafe.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterDetailedAirportUseCase {

    @Autowired
    private AirportRepository airportRepository;

    public Airport execute(DetailedAirportDto dto) {
        // 1. Fail-Fast: Criar o Value Object do ID e garantir maiúsculas
        IataCode iataCode = new IataCode(dto.iataCode.toUpperCase());

        // Validar se o aeroporto já existe na Base de Dados
        if (airportRepository.existsById(iataCode)) {
<<<<<<< HEAD
            throw new DuplicateResourceException("O Aeroporto com IATA " + dto.iataCode + " já existe!");
=======
            throw new IllegalArgumentException("O Aeroporto com IATA " + dto.iataCode + " já existe!");
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
        }

        // 2.Criar o objeto usando o CONSTRUTOR do Airport
        Airport airport = new Airport(
                iataCode,
                dto.name,
                dto.city,
                dto.country,
                dto.timezone,
<<<<<<< HEAD
                "OPERATIONAL"
=======
                "OPERATIONAL" // Status inicial por defeito
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
        );

        // 3. Adicionar as Instalações
        if (dto.facilities != null) {
            for (DetailedAirportDto.FacilityDto fDto : dto.facilities) {
                Facility facility = new Facility();
                facility.setType(fDto.type);
                facility.setCapacity(fDto.capacity);
                facility.setDescription(fDto.description);

<<<<<<< HEAD
                airport.addFacility(facility);
=======
                airport.addFacility(facility); // Usa o método do Airport.java
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
            }
        }

        // 4. Adicionar as Fotos
        if (dto.images != null) {
            for (DetailedAirportDto.ImageDto iDto : dto.images) {
                MediaImage image = new MediaImage();
                image.setImageUrl(iDto.imageUrl);
                image.setDescription(iDto.description);

<<<<<<< HEAD
                airport.addImage(image);
=======
                airport.addImage(image); // Usa o método do Airport.java
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
            }
        }

        // 5. Guardar tudo na BD
        return airportRepository.save(airport);
    }

    // --- Estrutura de Entrada do JSON (DTO) ---
    public static class DetailedAirportDto {
        public String iataCode;
        public String name;
        public String city;
        public String country;
        public String timezone;
        public List<FacilityDto> facilities;
        public List<ImageDto> images;

        public static class FacilityDto {
            public String type;
            public int capacity;
            public String description;
        }

        public static class ImageDto {
            public String imageUrl;
            public String description;
        }
    }
}