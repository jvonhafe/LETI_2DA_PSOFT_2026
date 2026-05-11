# Glossário - AISafe Flight Management System

Este glossário está sincronizado com o diagrama de modelo de domínio atualizado.  
Todos os conceitos aqui definidos correspondem diretamente às classes representadas no diagrama e refletem as necessidades operacionais e de histórico do sistema.

---

## Aircraft Domain

### Aircraft
*(Entity, Aggregate Root)* Representa uma instância física de um avião. É identificado unicamente pela sua matrícula (AircraftRegistration) e inclui informação como a data de fabrico, estado operacional e as suas métricas de utilização.

### AircraftRegistration
*(Value Object, Id)* Identificador único de um avião (matrícula), utilizado para distinguir instâncias de Aircraft dentro do sistema.

### SeatConfiguration
*(Value Object)* Define a configuração interna de lugares daquela instância específica, incluindo a divisão de lugares (ex: económica, executiva) e a capacidade total.

### OperationalMetrics
*(Value Object)* Regista os dados operacionais acumulados da instância, como o total de horas de voo e o limite de alcance atual.

### AircraftFeatures
*(Value Object)* Contém características adicionais e específicas daquela aeronave, como o tipo de motor instalado e se possui ligação Wi-Fi.

### AircraftStatus
*(Value Object)* Representa o estado operacional de um avião (ex: ativo, inativo, em manutenção). Controla a disponibilidade do avião para operações.

---

## Aircraft Model Domain

### AircraftModel
*(Entity, Aggregate Root)* Define um modelo de avião, incluindo características técnicas comuns a várias instâncias (Aircraft).

### AircraftModelId
*(Value Object, Id)* Identificador único de um modelo de avião, utilizado para distinguir diferentes modelos no sistema.

### Manufacturer
*(Value Object)* Representa o fabricante do avião (ex: Airbus, Boeing).

### AircraftSpecs
*(Value Object)* Contém as especificações técnicas do modelo de avião, incluindo capacidade máxima de passageiros, capacidade de combustível, alcance máximo e velocidade de cruzeiro.

### TechnicalDiagram
*(Entity)* Representa recursos visuais associados ao modelo, como diagramas técnicos ou manuais, para auxílio operacional.

---

## Airport Domain

### Airport
*(Entity, Aggregate Root)* Representa um aeroporto identificado por um código único (AirportCode). Gere a sua própria infraestrutura de pistas, certificações de aeronaves e inclui informação como nome, localização e estado.

### AirportCode
*(Value Object, Id)* Código único do aeroporto (IATA), utilizado para identificar de forma inequívoca um aeroporto no sistema.

### Location
*(Value Object)* Representa a localização geográfica do aeroporto, incluindo cidade, país, latitude e longitude.

### Timezone
*(Value Object)* Representa o fuso horário do aeroporto.

### AirportType
*(Value Object)* Define a classificação operacional ou tipo de aeroporto.

### AirportStatus
*(Value Object)* Indica o estado operacional do aeroporto (ex: operacional, encerrado, em manutenção).

### Runway
*(Entity)* Representa uma pista de um aeroporto, possuindo identidade própria dentro do aeroporto, incluindo características como nome de designação, comprimento e orientação.

### AircraftCertification
*(Entity)* Documento ou registo que indica que um determinado modelo de avião está autorizado a operar num aeroporto específico, com um período de validade definido (data de início e fim).

### MediaImage
*(Entity)* Fotografias ou representações visuais das instalações do aeroporto.

---

## Route Domain

### Route
*(Entity, Aggregate Root)* Representa uma rota aérea entre dois aeroportos. Define as condições necessárias para que um avião possa operar nessa rota e mantém o registo histórico das suas alterações.

### RouteId
*(Value Object, Id)* Identificador único da rota, utilizado para distinguir rotas no sistema.

### RouteRequirement
*(Value Object)* Define os requisitos mínimos para operar numa rota, como alcance mínimo e capacidade mínima exigida ao avião.

### RouteSchedule
*(Value Object)* Define os dias da semana em que a rota opera e a sua janela temporal planeada.

### RouteHistory
*(Entity)* Regista as versões anteriores e o histórico de modificações da rota, mantendo a rastreabilidade ao longo do tempo.

### FlightDuration
*(Value Object)* Representa a duração estimada de uma rota.

### RouteType
*(Value Object)* Classificação ou categoria da rota planeada.

### RouteStatus
*(Value Object)* Indica o estado da rota, contendo um indicador específico de que a rota está ou não ativa.

---

## Scheduled Flight Domain

### ScheduledFlight
*(Entity, Aggregate Root)* Representa a execução de uma rota num momento específico, associando um avião a uma rota numa determinada data e hora.

### FlightId
*(Value Object, Id)* Identificador único do voo, utilizado para distinguir voos no sistema.

### FlightSchedule
*(Value Object)* Define o horário do voo, incluindo data e hora de partida e chegada.

---

## Maintenance Template Domain

### MaintenanceTemplate
*(Entity, Aggregate Root)* Define um modelo de manutenção reutilizável, incluindo tipo e checklist de tarefas a realizar, aplicável a um ou mais modelos de avião.

### TemplateId
*(Value Object, Id)* Identificador único do template de manutenção, utilizado para distinguir templates no sistema.

### TemplateType
*(Value Object)* Tipo de manutenção (ex: inspeção, manutenção programada, revisão geral, modificação).

### Checklist
*(Value Object)* Lista de tarefas a executar durante a manutenção.

---

## Maintenance Record Domain

### MaintenanceRecord
*(Entity, Aggregate Root)* Representa um registo de manutenção em curso ou realizado num avião específico, incluindo descrição, notas de conclusão, agendamento e estado.

### RecordId
*(Value Object, Id)* Identificador único do registo de manutenção, utilizado para distinguir registos no sistema.

### MaintenanceSchedule
*(Value Object)* Define o planeamento temporal da manutenção, incluindo data de início e duração esperada.

### MaintenanceComponent
*(Value Object)* Identifica o componente afetado pela manutenção (ex: motor, fuselagem, avionics, interior).

### MaintenanceStatus
*(Value Object)* Indica o estado atual da manutenção (ex: em curso, concluída).

---

## Relações entre Conceitos

- Um **Aircraft** refere um **AircraftModel**
- Uma **Route** liga a dois **Airport** (Origem e Destino)
- Um **ScheduledFlight** associa um **Aircraft** a uma **Route**
- Um **MaintenanceRecord** aplica-se a um **Aircraft** e utiliza um **MaintenanceTemplate**
- Um **MaintenanceTemplate** pode aplicar-se a vários **AircraftModel**

---

## Notas de Modelação

- As **Entities** são identificadas por um identificador único e possuem identidade e ciclo de vida próprios (incluindo entidades locais dentro de agregados, como *Runway* e *RouteHistory*).
- Os **Value Objects** são definidos apenas pelos seus atributos, não têm identidade própria e são imutáveis.
- Os **Aggregates** definem limites de consistência lógica. Cada agregado é acedido unicamente através da sua Entidade Principal (**Aggregate Root**).
- As relações *entre* agregados diferentes são sempre realizadas através de identificadores (ex: `Route` guarda o ID de `Airport`), garantindo o baixo acoplamento estipulado pelo Domain-Driven Design (DDD).