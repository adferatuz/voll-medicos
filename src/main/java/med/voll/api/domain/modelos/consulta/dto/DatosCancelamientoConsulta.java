package med.voll.api.domain.modelos.consulta.dto;

import jakarta.persistence.Enumerated;
import med.voll.api.domain.modelos.consulta.MotivoCancelamiento;

public record DatosCancelamientoConsulta(

        Long idConsulta,

        @Enumerated
        MotivoCancelamiento motivo
) {
}
