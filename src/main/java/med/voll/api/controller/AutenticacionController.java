package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.domain.usuarios.dto.DatosAutenticacionUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuarios datosAutenticacionUsuarios){

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuarios.login(), datosAutenticacionUsuarios.clave());
        var usuarioAutenticado = authenticationManager.authenticate((authenticationToken));
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return  ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}