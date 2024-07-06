package med.voll.api.domain.modelos.consulta.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.modelos.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(

        Long id,

        Long idPaciente,

        Long idMedico,

        @NotNull
        LocalDateTime fecha,

        Especialidad especialidad
) {
}
