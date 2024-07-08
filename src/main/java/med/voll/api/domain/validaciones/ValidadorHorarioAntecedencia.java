package med.voll.api.domain.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.dto.DatosCancelamientoConsulta;
import med.voll.api.domain.repositorio.ConsultaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsultas{

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @Override
    public void validar(DatosCancelamientoConsulta datos) {
        var consulta = consultaRepositorio.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getData()).toHours();

        if (diferenciaEnHoras < 24){
            throw new ValidationException("Consulta solamente puede ser cancelada con antecedencia minima de 24 horas");
        }
    }
}
