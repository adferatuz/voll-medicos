package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.repositorio.ConsultaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    public void validar(DatosAgendarConsulta datos) {

        if (datos.idMedico() == null){
            return;
        }

        var medicoConConsulta = consultaRepositorio.existsByMedicoIdAndDatetime(datos.idMedico(), datos.fecha());

        if (medicoConConsulta){
            throw new ValidationException("El médico ya tiene otra cita programada en la misma fecha/hora;");
        }

    }
}
