-- =========================================================================
-- NIVEL 1: TABLAS MAESTRAS que no dependen de nadie
-- =========================================================================

-- 1. Países
INSERT IGNORE INTO pais (nombre) VALUES ('Reino Unido'); -- ID 1
INSERT IGNORE INTO pais (nombre) VALUES ('Italia');      -- ID 2
INSERT IGNORE INTO pais (nombre) VALUES ('Austria');     -- ID 3
INSERT IGNORE INTO pais (nombre) VALUES ('Argentina');   -- ID 4
INSERT IGNORE INTO pais (nombre) VALUES ('Mónaco');      -- ID 5
INSERT IGNORE INTO pais (nombre) VALUES ('España');      -- ID 6
INSERT IGNORE INTO pais (nombre) VALUES ('Francia');     -- ID 7
INSERT IGNORE INTO pais (nombre) VALUES ('Tailandia');   -- ID 8
INSERT IGNORE INTO pais (nombre) VALUES ('Australia');   -- ID 9
INSERT IGNORE INTO pais (nombre) VALUES ('Japon');       -- ID 10
INSERT IGNORE INTO pais (nombre) VALUES ('Estados Unidos');   -- ID 11

-- 2. Motoristas
INSERT IGNORE INTO motorista (nombre) VALUES ('Mercedes');   -- ID 1
INSERT IGNORE INTO motorista (nombre) VALUES ('Ferrari');    -- ID 2
INSERT IGNORE INTO motorista (nombre) VALUES ('Red Bull Ford Powertrains'); -- ID 3
INSERT IGNORE INTO motorista (nombre) VALUES ('Audi');    -- ID 4

-- 3. Números de Piloto (Dejamos algunos disponibles y otros listos para usar)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (3, true);  -- ID 1 (Max)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (16, true); -- ID 2 (Charles)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (44, true); -- ID 3 (Lewis)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (43, true); -- ID 4 (Franco)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (55, true); -- ID 5 (Carlos)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (23, true); -- ID 6 (Albon)
INSERT IGNORE INTO numero_piloto (valor, esta_disponible) VALUES (10, true); -- ID 7 (Gasly)



-- =========================================================================
-- NIVEL 2: ENTIDADES INTERMEDIAS (Dependen del Nivel 1)
-- =========================================================================

-- 1. Escuderías (Vinculamos País y Motorista mediante sus IDs)
-- Mercedes (UK, Motor Mercedes)
INSERT IGNORE INTO escuderia (nombre, pais_id, jefe_equipo, anio_fundacion, motorista_id, tipo_motorista, creado_en, actualizado_en) 
VALUES ('Atlassian Williams Racing', 1, 'James Vowles', 1977, 1, 'CLIENTE', NOW(), NOW());

-- Ferrari (Italia, Motor Ferrari)
INSERT IGNORE INTO escuderia (nombre, pais_id, jefe_equipo, anio_fundacion, motorista_id, tipo_motorista, creado_en, actualizado_en) 
VALUES ('Scuderia Ferrari', 2, 'Frédéric Vasseur', 1950, 2, 'PROVEEDOR', NOW(), NOW());

-- Red Bull (Austria, Motor Honda)
INSERT IGNORE INTO escuderia (nombre, pais_id, jefe_equipo, anio_fundacion, motorista_id, tipo_motorista, creado_en, actualizado_en) 
VALUES ('Alpine', 7, 'Steve Nielsen', 2021, 3, 'CLIENTE', NOW(), NOW());


-- =========================================================================
-- NIVEL 3: SEGURIDAD Y EVENTOS (Dependen del Nivel 1 y 2)
-- =========================================================================

-- 1. Usuarios (Directores de Equipo vinculados 1:1 a su escudería)
-- NOTA TÉCNICA: En producción, estos passwords DEBEN estar hasheados con BCrypt. 
-- Para esta prueba inicial pondremos '1234' para que puedas probar el login luego.
INSERT IGNORE INTO usuario (username, password, escuderia_id, fecha_registro, actualizado_en)
VALUES ('james_vowles', '1234', 1, NOW(), NOW());

INSERT IGNORE INTO usuario (username, password, escuderia_id, fecha_registro, actualizado_en)
VALUES ('fred_vasseur', '1234', 2, NOW(), NOW());

INSERT IGNORE INTO usuario (username, password, escuderia_id, fecha_registro, actualizado_en)
VALUES ('flavio_briatore', '1234', 3, NOW(), NOW());

-- 2. Carreras Históricas (Catálogo de consulta)
INSERT IGNORE INTO carrera (nombre_gp, pais_id, longitud_km, vueltas, es_sprint, fecha, nombre_circuito, creado_en)
VALUES ('Gran Premio de Gran Bretaña', 1, 5.891, 52, false, '2026-07-05 10:00:00', 'Silverstone', NOW());

INSERT IGNORE INTO carrera (nombre_gp, pais_id, longitud_km, vueltas, es_sprint, fecha, nombre_circuito, creado_en)
VALUES ('Gran Premio de Italia', 2, 5.793, 53, false, '2026-09-06 10:00:00', 'Monza', NOW());