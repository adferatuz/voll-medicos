package med.voll.api.domain.modelos.paciente.dto;

import med.voll.api.domain.modelos.paciente.Paciente;

public record DatosListadoPaciente(

        Long id,

        String nombre,

        String documento,

        String email
) {

    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(),paciente.getNombre(),paciente.getDocumento(), paciente.getEmail());
    }
}
