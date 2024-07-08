package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.repositorio.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    public void validar(DatosAgendarConsulta datos) {

        if (datos.idPaciente() == null){
            return;
        }

        var pacienteActivo = pacienteRepositorio.findActivoById(datos.idPaciente());

        if (!pacienteActivo){
            throw new ValidationException("No permitir agendar citas con pacientes inactivos en el sistema");
        }

    }
}
