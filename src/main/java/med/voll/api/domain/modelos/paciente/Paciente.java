package med.voll.api.domain.modelos.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.modelos.medico.Direccion;
import med.voll.api.domain.modelos.paciente.dto.DatosActualizarPaciente;
import med.voll.api.domain.modelos.paciente.dto.DatosRegistroPaciente;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    private boolean activo;

    @Embedded
    private Direccion direccion;

    public Paciente(@Valid DatosRegistroPaciente datosRegistroPaciente) {
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.documento = datosRegistroPaciente.documento();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void desactivarPaciente() {
        this.activo= false;
    }

    public void activarPaciente() {
        this.activo= true;
    }

    public void actualizarPaciente(DatosActualizarPaciente datosActualizarPaciente) {

        if (datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }

        if (datosActualizarPaciente.documento() != null){
            this.documento = datosActualizarPaciente.documento();
        }

        if (datosActualizarPaciente.direccion() != null){
            this.direccion = direccion.actualizarDatos(datosActualizarPaciente.direccion());
        }
    }
}
