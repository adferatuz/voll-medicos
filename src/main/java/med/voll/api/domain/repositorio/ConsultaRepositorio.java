package med.voll.api.domain.repositorio;

import med.voll.api.domain.modelos.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepositorio extends JpaRepository<Consulta,Long> {
}
