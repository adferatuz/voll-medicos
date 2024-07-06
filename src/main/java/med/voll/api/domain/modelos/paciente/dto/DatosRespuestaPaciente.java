package med.voll.api.domain.modelos.paciente.dto;

import med.voll.api.domain.modelos.medico.dto.DatosDireccion;

public record DatosRespuestaPaciente(

        Long id,

        String nombre,

        String email,

        String telefono,

        String documento,

        DatosDireccion direccion
) {
}
