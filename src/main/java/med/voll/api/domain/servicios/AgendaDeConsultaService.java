package med.voll.api.domain.servicios;

import jakarta.validation.ValidationException;
import med.voll.api.domain.modelos.consulta.Consulta;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.modelos.consulta.dto.DatosCancelamientoConsulta;
import med.voll.api.domain.modelos.consulta.dto.DatosDetalleConsulta;
import med.voll.api.domain.modelos.medico.Medico;
import med.voll.api.domain.repositorio.ConsultaRepositorio;
import med.voll.api.domain.repositorio.MedicoRepositorio;
import med.voll.api.domain.repositorio.PacienteRepositorio;
import med.voll.api.domain.validaciones.ValidadorCancelamientoDeConsultas;
import med.voll.api.domain.validaciones.ValidadorDeConsultas;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    @Autowired
    private List<ValidadorCancelamientoDeConsultas> validadorCancelamientoDeConsultas;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){

        if (!pacienteRepositorio.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidationException("Este id para el paciente no fue encontrado");
        }

        if (datosAgendarConsulta.idMedico() != null && !medicoRepositorio.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidationException("Este id para el paciente no fue encontrado");
        }

        validadores.forEach(v -> v.validar(datosAgendarConsulta));

        var paciente = pacienteRepositorio.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);

        if (medico == null){
            throw new ValidacionDeIntegridad("No existen medicos disponibles para este horario y especialidad");

        }

        var consulta = new Consulta(medico, paciente, datosAgendarConsulta.fecha());

        consultaRepositorio.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if (datosAgendarConsulta.idMedico() != null){
            return medicoRepositorio.getReferenceById(datosAgendarConsulta.idMedico());
        }

        if (datosAgendarConsulta.especialidad() == null) {
            throw  new ValidationException("Debe seleccionar una especialidad para el medico");
        }

        return medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha(
                datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }

    public void cancelar(DatosCancelamientoConsulta datos) {

        if (!consultaRepositorio.existsById(datos.idConsulta())) {
            throw new ValidacionDeIntegridad("Id de la consulta informado no existe!");
        }

        validadorCancelamientoDeConsultas.forEach(v->v.validar(datos));

        var consulta = consultaRepositorio.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
