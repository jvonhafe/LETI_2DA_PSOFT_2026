<<<<<<< HEAD
-- 1. CRIAR OS UTILIZADORES (Para testares a Segurança JWT no Postman)
-- A password de todos é 'password123' (já encriptada ou validada no teu AuthController)
INSERT INTO users (username, password, role) VALUES ('backoffice', 'password123', 'ROLE_BACKOFFICE');
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ROLE_ADMIN');
INSERT INTO users (username, password, role) VALUES ('atcc_user', 'atcc123', 'ROLE_ATCC');

-- 2. CRIAR AEROPORTOS (Dados Realistas)
INSERT INTO airport (iata_code, name, city, country, timezone, status, email, phone_number, opening_time, closing_time)
VALUES ('OPO', 'Francisco Sá Carneiro', 'Porto', 'Portugal', 'WET', 'OPERATIONAL', 'info@porto.pt', '+351229432400', '04:00', '00:00');

INSERT INTO airport (iata_code, name, city, country, timezone, status, email, phone_number, opening_time, closing_time)
VALUES ('LIS', 'Humberto Delgado', 'Lisbon', 'Portugal', 'WET', 'OPERATIONAL', 'info@lisbon.pt', '+351218413500', '00:00', '23:59');

INSERT INTO airport (iata_code, name, city, country, timezone, status, email, phone_number, opening_time, closing_time)
VALUES ('MAD', 'Adolfo Suárez Madrid-Barajas', 'Madrid', 'Spain', 'CET', 'OPERATIONAL', 'info@madrid.es', '+34913211000', '00:00', '23:59');

INSERT INTO airport (iata_code, name, city, country, timezone, status, email, phone_number, opening_time, closing_time)
VALUES ('JFK', 'John F. Kennedy International', 'New York', 'USA', 'EST', 'OPERATIONAL', 'info@jfk.com', '+17182444444', '00:00', '23:59');

-- 3. CRIAR INSTALAÇÕES PARA O AEROPORTO DO PORTO (US207)
INSERT INTO facility (airport_iata, type, capacity, description) VALUES ('OPO', 'Terminal', 10000, 'Terminal Principal');
INSERT INTO facility (airport_iata, type, capacity, description) VALUES ('OPO', 'Gate', 150, 'Porta 14 - Voos Schengen');

-- 4. CRIAR IMAGENS PARA O AEROPORTO DO PORTO (US207)
-- Nota: Num backend RESTful normal, a imagem é apenas um link guardado na BD.
INSERT INTO media_image (airport_iata, image_url, description) VALUES ('OPO', 'https://example.com/opo_fachada.jpg', 'Fachada do Aeroporto');

-- 5. CRIAR ROTAS (Para testar as estatísticas US209 e US210)
-- OPO para LIS, LIS para MAD, etc.
INSERT INTO route (origin_id, destination_id, minimum_range, minimum_capacity, is_active) VALUES ('OPO', 'LIS', 300, 50, true);
INSERT INTO route (origin_id, destination_id, minimum_range, minimum_capacity, is_active) VALUES ('LIS', 'MAD', 500, 100, true);
INSERT INTO route (origin_id, destination_id, minimum_range, minimum_capacity, is_active) VALUES ('MAD', 'JFK', 6000, 200, true);
INSERT INTO route (origin_id, destination_id, minimum_range, minimum_capacity, is_active) VALUES ('OPO', 'MAD', 450, 80, true);
=======
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
>>>>>>> 987052eedb031394fba250f4e0e571285ef997aa
