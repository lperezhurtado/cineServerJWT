package net.ausiasmarch.cineServerJWT.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.cineServerJWT.bean.UsuarioBean;
import net.ausiasmarch.cineServerJWT.entities.UsuarioEntity;
import net.ausiasmarch.cineServerJWT.services.AuthService;

@RestController
@RequestMapping("/login")
public class LoginControl {
    
    @Autowired
    AuthService authService;

    //login con JWT
    @PostMapping
    public ResponseEntity<String> login(@RequestBody UsuarioBean usuarioBean) {
        return new ResponseEntity<String>(authService.loginJWT(usuarioBean), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<UsuarioEntity> check() {
        return new ResponseEntity<UsuarioEntity>(authService.checkJWT(), HttpStatus.OK);
    }
}
