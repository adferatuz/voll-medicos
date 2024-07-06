package med.voll.api.domain.servicios;

import med.voll.api.domain.modelos.consulta.Consulta;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.modelos.medico.Medico;
import med.voll.api.domain.repositorio.ConsultaRepositorio;
import med.voll.api.domain.repositorio.MedicoRepositorio;
import med.voll.api.domain.repositorio.PacienteRepositorio;
import med.voll.api.infra.errores.TratadorDeErrores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private ConsultaRepositorio consultaRepositorio;

    public void agendar(DatosAgendarConsulta datosAgendarConsulta){

        if (pacienteRepositorio.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new TratadorDeErrores("Este id para el paciente no fue encontrado");
        }

        if (datosAgendarConsulta.idMedico() != null && medicoRepositorio.existsById(datosAgendarConsulta.idMedico())){
            throw new TratadorDeErrores("Este id para el paciente no fue encontrado");
        }

        var paciente = pacienteRepositorio.findById(datosAgendarConsulta.idPaciente()).get();

        var medico = seleccionarMedico(datosAgendarConsulta);

        var consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());

        consultaRepositorio.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {

        if (datosAgendarConsulta.idMedico() != null){
            return medicoRepositorio.getReferenceById(datosAgendarConsulta.idMedico());
        }

        if (datosAgendarConsulta.especialidad() == null) {
            throw  new TratadorDeErrores("Debe seleccionar una especialidad para el medico");
        }

        return medicoRepositorio.seleccionarMedicoConEspecialidadEnFecha(
                datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}
