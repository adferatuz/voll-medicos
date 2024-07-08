package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.modelos.medico.Medico;
import med.voll.api.domain.modelos.medico.dto.*;
import med.voll.api.domain.repositorio.MedicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedicos(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                                 UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepositorio.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return  ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>>  listadoMedicos(/*@PageableDefault(size = 2)*/ Pageable paginacion){
  //      return  medicoRepositorio.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepositorio.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizaMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepositorio.getReferenceById(datosActualizarMedico.id());
        medico.actualizarMedico(datosActualizarMedico);
        return ResponseEntity.ok(
                new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero())));

    }

    //METODO PARA ELIMINAR RECURSOS DE FORMA LOGICA(NO ELIMINA DE LA BASE DE DATOS, SOLO CAMBIA EL ESTADO PARA QUE NO
    //SE MUESTRE)
    /*

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepositorio.getReferenceById(id);
        medico.desactivarMedico();

    }
    */

    //DEVOLVER UNA RESPUESTA DESPUES DE HACER UN DELETE DE LA BASE DE DATOS EN PROTOCOLO HTTPS
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepositorio.getReferenceById(id);
        medico.desactivarMedico();
        return  ResponseEntity.noContent().build();

    }

    //METODO PARA ELIMINAR RECURSOS  DE LA BASE DE DATOS POR MEDIO DE EL ID
    /*@DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepositorio.getReferenceById(id);
        medicoRepositorio.delete(medico);

    }*/

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepositorio.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getComplemento(),
                        medico.getDireccion().getNumero()));
        return  ResponseEntity.ok(datosMedico);

    }
}
