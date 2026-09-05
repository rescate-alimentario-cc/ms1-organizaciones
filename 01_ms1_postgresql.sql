-- =====================================================================
-- MS1 - Organizaciones
-- Motor: PostgreSQL 16     Base: organizaciones
-- Ejecutar dentro de la MV-BD:
--   docker exec -it <contenedor_postgres> psql -U postgres -d organizaciones
-- =====================================================================

DROP TABLE IF EXISTS sede;
DROP TABLE IF EXISTS organizacion;

CREATE TABLE organizacion (
    organizacion_id  SERIAL PRIMARY KEY,
    nombre           VARCHAR(150) NOT NULL,
    tipo             VARCHAR(10)  NOT NULL
                     CHECK (tipo IN ('DONANTE', 'ONG')),
    ruc              CHAR(11)     NOT NULL UNIQUE,
    categoria        VARCHAR(30)  NOT NULL,
    email            VARCHAR(120),
    telefono         VARCHAR(20),
    fecha_registro   DATE         NOT NULL DEFAULT CURRENT_DATE,
    activo           BOOLEAN      NOT NULL DEFAULT TRUE
);

COMMENT ON COLUMN organizacion.tipo IS 'DONANTE | ONG';
COMMENT ON COLUMN organizacion.categoria IS
    'Donantes: SUPERMERCADO, RESTAURANTE, PANADERIA, MERCADO. '
    'ONG: COMEDOR_POPULAR, ALBERGUE, BANCO_ALIMENTOS';

CREATE TABLE sede (
    sede_id          SERIAL PRIMARY KEY,
    organizacion_id  INTEGER      NOT NULL
                     REFERENCES organizacion(organizacion_id)
                     ON DELETE CASCADE,
    nombre           VARCHAR(150) NOT NULL,
    distrito         VARCHAR(60)  NOT NULL,
    direccion        VARCHAR(200),
    latitud          DECIMAL(10,7),
    longitud         DECIMAL(10,7),
    horario_atencion VARCHAR(60),
    capacidad_kg     DECIMAL(10,2),
    es_principal     BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_sede_organizacion ON sede(organizacion_id);
CREATE INDEX idx_sede_distrito     ON sede(distrito);
CREATE INDEX idx_org_tipo          ON organizacion(tipo);
CREATE INDEX idx_org_categoria     ON organizacion(categoria);

-- Verificacion
-- SELECT COUNT(*) FROM organizacion;
-- SELECT COUNT(*) FROM sede;
