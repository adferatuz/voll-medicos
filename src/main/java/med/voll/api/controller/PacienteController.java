package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.modelos.medico.Medico;
import med.voll.api.domain.modelos.paciente.Paciente;
import med.voll.api.domain.modelos.medico.dto.*;
import med.voll.api.domain.modelos.paciente.dto.DatosActualizarPaciente;
import med.voll.api.domain.modelos.paciente.dto.DatosListadoPaciente;
import med.voll.api.domain.modelos.paciente.dto.DatosRegistroPaciente;
import med.voll.api.domain.modelos.paciente.dto.DatosRespuestaPaciente;
import med.voll.api.domain.repositorio.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>>  listadoMedicos( Pageable paginacion){
        return ResponseEntity.ok(pacienteRepositorio.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornaDatosPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepositorio.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getNumero()));
        return  ResponseEntity.ok(datosPaciente);

    }

    @PostMapping
    public ResponseEntity<DatosRespuestaPaciente> registrarPacientes(
            @RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
            UriComponentsBuilder uriComponentsBuilder){
        System.out.println("Estos son los datos que llegan: " + datosRegistroPaciente);
        Paciente paciente = pacienteRepositorio.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),
                paciente.getNombre(), paciente.getEmail(),
                paciente.getTelefono(), paciente.getDocumento(),
                new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                        paciente.getDireccion().getCiudad(), paciente.getDireccion().getComplemento(),
                        paciente.getDireccion().getNumero()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return  ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizaPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepositorio.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarPaciente(datosActualizarPaciente);
        return ResponseEntity.ok(
                new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                        paciente.getTelefono(), paciente.getDocumento(),
                        new DatosDireccion(paciente.getDireccion().getCalle(), paciente.getDireccion().getDistrito(),
                                paciente.getDireccion().getCiudad(), paciente.getDireccion().getComplemento(),
                                paciente.getDireccion().getNumero())));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepositorio.getReferenceById(id);
        paciente.desactivarPaciente();
        return  ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activar")
    @Transactional
    public ResponseEntity activarPaciente(@PathVariable Long id) {
        Optional<Paciente> paciente = pacienteRepositorio.findById(id);
        paciente.get().activarPaciente();
        pacienteRepositorio.save(paciente.get());

        return ResponseEntity.ok().build();
    }





}
