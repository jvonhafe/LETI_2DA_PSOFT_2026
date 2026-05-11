# Glossário - AISafe Flight Management System

Este glossário está sincronizado com o diagrama de modelo de domínio apresentado.  
Todos os conceitos aqui definidos correspondem diretamente às classes representadas no diagrama.

---

## Aircraft Domain

### Aircraft
*(Entity, Aggregate Root)*  
Representa uma instância física de um avião. É identificado unicamente pela sua matrícula (AircraftRegistration) e inclui informação como a data de fabrico e o estado operacional.

### AircraftRegistration
*(Value Object)*  
Identificador único de um avião (matrícula), utilizado para distinguir instâncias de Aircraft dentro do sistema.

### AircraftStatus
*(Value Object)*  
Representa o estado operacional de um avião (ex: ativo, inativo, em manutenção). Controla a disponibilidade do avião para operações.

---

## Aircraft Model Domain

### AircraftModel
*(Entity, Aggregate Root)*  
Define um modelo de avião, incluindo características técnicas comuns a várias instâncias (Aircraft).

### AircraftModelId
*(Value Object)*  
Identificador único de um modelo de avião, utilizado para distinguir diferentes modelos no sistema.

### Manufacturer
*(Value Object)*  
Representa o fabricante do avião (ex: Airbus, Boeing).

### AircraftSpecs
*(Value Object)*  
Contém as especificações técnicas do modelo de avião, incluindo capacidade de passageiros (seatingCapacity), capacidade de combustível (fuelCapacity), alcance máximo (maxRange) e velocidade de cruzeiro (cruisingSpeed).

---

## Airport Domain

### Airport
*(Entity, Aggregate Root)*  
Representa um aeroporto identificado por um código único (AirportCode). Inclui informação como nome, localização, estado e infraestrutura.

### AirportCode
*(Value Object)*  
Código único do aeroporto (IATA), utilizado para identificar de forma inequívoca um aeroporto no sistema.

### Location
*(Value Object)*  
Representa a localização geográfica do aeroporto, incluindo cidade, país, latitude e longitude.

### Timezone
*(Value Object)*  
Representa o fuso horário do aeroporto.

### AirportStatus
*(Value Object)*  
Indica o estado operacional do aeroporto (ex: operacional, encerrado, em manutenção).

### Runway
*(Value Object)*  
Representa uma pista de um aeroporto, incluindo características como identificação e comprimento.

### AircraftCertification
*(Value Object)*  
Indica que um determinado modelo de avião está autorizado a operar num aeroporto específico.

---

## Route Domain

### Route
*(Entity, Aggregate Root)*  
Representa uma rota aérea entre dois aeroportos. Define as condições necessárias para que um avião possa operar nessa rota.

### RouteId
*(Value Object)*  
Identificador único da rota, utilizado para distinguir rotas no sistema.

### RouteRequirement
*(Value Object)*  
Define os requisitos mínimos para operar numa rota, como alcance mínimo e capacidade mínima do avião.

### FlightDuration
*(Value Object)*  
Representa a duração estimada de uma rota.

### RouteStatus
*(Value Object)*  
Indica o estado da rota (ex: ativa, inativa).

---

## Scheduled Flight Domain

### ScheduledFlight
*(Entity, Aggregate Root)*  
Representa a execução de uma rota num momento específico, associando um avião a uma rota numa determinada data e hora.

### FlightId
*(Value Object)*  
Identificador único do voo, utilizado para distinguir voos no sistema.

### FlightSchedule
*(Value Object)*  
Define o horário do voo, incluindo data e hora de partida e chegada.

---

## Maintenance Template Domain

### MaintenanceTemplate
*(Entity, Aggregate Root)*  
Define um modelo de manutenção reutilizável, incluindo tipo e checklist de tarefas a realizar.

### TemplateId
*(Value Object)*  
Identificador único do template de manutenção, utilizado para distinguir templates no sistema.

### TemplateType
*(Value Object)*  
Tipo de manutenção (ex: inspeção, manutenção programada, revisão geral, modificação).

### Checklist
*(Value Object)*  
Lista de tarefas a executar durante a manutenção.

---

## Maintenance Record Domain

### MaintenanceRecord
*(Entity, Aggregate Root)*  
Representa um registo de manutenção realizado num avião específico, incluindo descrição, duração e estado.

### RecordId
*(Value Object)*  
Identificador único do registo de manutenção, utilizado para distinguir registos no sistema.

### MaintenanceSchedule
*(Value Object)*  
Define o período da manutenção, incluindo data de início e duração esperada.

### MaintenanceComponent
*(Value Object)*  
Identifica o componente afetado pela manutenção (ex: motor, fuselagem, avionics, interior).

### MaintenanceStatus
*(Value Object)*  
Indica o estado da manutenção (ex: em curso, concluída).

---

## Relações entre Conceitos

- Um **Aircraft** está associado a um **AircraftModel**
- Um **Route** liga dois **Airport**
- Um **ScheduledFlight** associa um **Aircraft** a uma **Route**
- Um **MaintenanceRecord** está associado a um **Aircraft**
- Um **MaintenanceTemplate** pode aplicar-se a vários **AircraftModel**

---

## Notas de Modelação

- As **Entities** são identificadas por um identificador único e possuem identidade própria.
- Os **Value Objects** são definidos apenas pelos seus atributos e são imutáveis.
- Os **Aggregates** definem limites de consistência e são acedidos através dos seus Aggregate Roots.
- As relações entre agregados são realizadas através de identificadores (Value Objects), evitando dependências diretas entre agregados e garantindo baixo acoplamento.

---