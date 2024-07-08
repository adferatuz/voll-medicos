CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    datetime TIMESTAMP NOT NULL,
    CONSTRAINT fk_consultas_medico_id
        FOREIGN KEY (medico_id)
        REFERENCES medicos (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_consultas_paciente_id
        FOREIGN KEY (paciente_id)
        REFERENCES pacientes (id)
        ON DELETE CASCADE
);
