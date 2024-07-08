package med.voll.api.domain.repositorio;

import med.voll.api.domain.modelos.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta,Long> {


    Boolean existsByPacienteIdAndDatetimeBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);


    Boolean existsByMedicoIdAndDatetime(Long idMedico, LocalDateTime datetime);
}
