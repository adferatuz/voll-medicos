CREATE TABLE consultas (
    id SERIAL PRIMARY KEY,
    medico_id INT NOT NULL,
    paciente_id INT NOT NULL,
    datetime TIMESTAMP NOT NULL,
    CONSTRAINT fk_medico
        FOREIGN KEY(medico_id)
        REFERENCES medicos(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_paciente
        FOREIGN KEY(paciente_id)
        REFERENCES pacientes(id)
        ON DELETE CASCADE
);
