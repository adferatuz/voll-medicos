package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.repositorio.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas {

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    public void validar(DatosAgendarConsulta datos) {

        if (datos.idMedico() == null){
            return;
        }

        var medicoActivo = medicoRepositorio.findActivoById(datos.idMedico());

        if (!medicoActivo){
            throw new ValidationException("No permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
