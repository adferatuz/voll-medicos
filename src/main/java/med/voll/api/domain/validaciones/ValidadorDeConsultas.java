package med.voll.api.domain.validaciones;

import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;

public interface ValidadorDeConsultas {

    public void validar(DatosAgendarConsulta datos);
}
