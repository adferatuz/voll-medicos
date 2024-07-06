package med.voll.api.domain.modelos.paciente.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.modelos.medico.dto.DatosDireccion;

public record DatosRegistroPaciente(

        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefono,

        @NotBlank
        String documento,

        @NotNull
        @Valid
        DatosDireccion direccion
) {
}
