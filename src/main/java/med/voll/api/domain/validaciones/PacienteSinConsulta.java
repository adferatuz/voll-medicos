package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.repositorio.ConsultaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    public void validar(DatosAgendarConsulta datos) {

        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = consultaRepositorio.existsByPacienteIdAndDatetimeBetween(
                datos.idPaciente(), primerHorario, ultimoHorario);

        if (pacienteConConsulta){
            throw new ValidationException("Ya existe una consulta en el mismo día para el mismo paciente");
        }


    }

}
