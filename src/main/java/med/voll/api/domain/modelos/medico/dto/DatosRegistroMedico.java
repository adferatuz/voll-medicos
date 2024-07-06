package med.voll.api.domain.modelos.medico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.modelos.medico.Especialidad;

public record DatosRegistroMedico(
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
        Especialidad especialidad,

        @NotNull
        @Valid
        DatosDireccion direccion
) {
}
