package med.voll.api.domain.modelos.medico.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
         @NotBlank
         String calle,

         @NotBlank
         String distrito,

         @NotBlank
         String ciudad,

         @NotBlank
         String numero,

         @NotBlank
         String complemento
) {
}
