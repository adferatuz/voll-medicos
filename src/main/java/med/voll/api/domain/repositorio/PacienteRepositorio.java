package med.voll.api.domain.repositorio;

import med.voll.api.domain.modelos.medico.Medico;
import med.voll.api.domain.modelos.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Long> {
    Page<Paciente> findByActivoTrue(Pageable paginacion);
}
