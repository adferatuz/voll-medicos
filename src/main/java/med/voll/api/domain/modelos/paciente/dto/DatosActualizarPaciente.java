package med.voll.api.domain.modelos.paciente.dto;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.modelos.medico.dto.DatosDireccion;

public record DatosActualizarPaciente(

        @NotNull
        Long id,

        String nombre,

        String documento,

        DatosDireccion direccion
) {
}
