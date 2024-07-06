package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.modelos.consulta.dto.DatosDetalleConsulta;
import med.voll.api.domain.servicios.AgendaDeConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        service.agendar(datos);

        return  ResponseEntity.ok( new DatosDetalleConsulta(null, null, null, null));
    }
}
