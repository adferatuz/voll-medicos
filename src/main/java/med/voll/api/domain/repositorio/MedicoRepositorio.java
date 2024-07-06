package med.voll.api.domain.repositorio;

import med.voll.api.domain.modelos.medico.Especialidad;
import med.voll.api.domain.modelos.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepositorio extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo = true and
            m.especialidad = :especialidad and
            m.id NOT INT(
            SELECT c.medico.id FROM Consulta c
            d.dateTime = :fecha)
            ORDER BY rand()
            LIMIT 1 
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);
}
