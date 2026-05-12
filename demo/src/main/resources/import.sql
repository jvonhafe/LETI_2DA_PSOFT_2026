-- 1. Bootstrap de Utilizadores (WP #0A)
-- Se já tiveres a tabela 'users' criada
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
INSERT INTO users (username, password, role) VALUES ('atcc_user', 'password123', 'ATCC');

-- 2. Bootstrap de Fabricantes e Modelos (WP #0A / WP #1A)
-- Isto ajuda a Pessoa 2 (Aeronaves)
INSERT INTO manufacturers (id, name) VALUES (1, 'Airbus'), (2, 'Boeing'), (3, 'Embraer');

-- 3. Bootstrap de Aeroportos (WP #2A)
-- Isto ajuda a Pessoa 3 (Rotas) a ter destinos para testar
INSERT INTO airport (iata_code, name, city, country, status) VALUES ('LIS', 'Aeroporto Humberto Delgado', 'Lisboa', 'Portugal', 'OPERATIONAL');
INSERT INTO airport (iata_code, name, city, country, status) VALUES ('OPO', 'Aeroporto Francisco Sá Carneiro', 'Porto', 'Portugal', 'OPERATIONAL');
INSERT INTO airport (iata_code, name, city, country, status) VALUES ('CDG', 'Charles de Gaulle', 'Paris', 'França', 'OPERATIONAL');

-- 4. Templates de Manutenção (WP #4A)
-- Isto ajuda a Pessoa 4
INSERT INTO maintenance_templates (name, type) VALUES ('Check A', 'INSPECTION'), ('Check B', 'SCHEDULED');