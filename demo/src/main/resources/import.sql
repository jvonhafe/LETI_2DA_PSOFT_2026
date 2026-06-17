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