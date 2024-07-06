package med.voll.api.domain.modelos.medico.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record DatosRespuestaMedico(

        Long id,

        String nombre,

        String email,

        String telefono,

        String documento,

        String especialidad,

        DatosDireccion direccion
) {
}
