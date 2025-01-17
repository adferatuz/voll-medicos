package med.voll.api.domain.modelos.consulta.dto;

import med.voll.api.domain.modelos.consulta.Consulta;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(

        Long id,

        Long idPaciente,

        Long idMedico,

        LocalDateTime fecha
) {
    public DatosDetalleConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(),consulta.getMedico().getId(),consulta.getData());

    }
}
