package net.ausiasmarch.cineServerJWT.services;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import net.ausiasmarch.cineServerJWT.bean.UsuarioBean;
import net.ausiasmarch.cineServerJWT.entities.UsuarioEntity;
import net.ausiasmarch.cineServerJWT.exceptions.UnauthorizationException;
import net.ausiasmarch.cineServerJWT.helper.JWTHelper;
import net.ausiasmarch.cineServerJWT.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    UsuarioRepository usuarioRepo;

    //login con JWT
    public String loginJWT(@RequestBody UsuarioBean usuarioBean) {

        if (usuarioBean.getPassword() == null) {
            throw new UnauthorizationException("Usuario o contraseña errónea");
        }

        UsuarioEntity usuarioLogin = usuarioRepo.findByLoginAndPassword(usuarioBean.getLogin(), usuarioBean.getPassword());
        if (usuarioLogin != null) {
            return JWTHelper.generateJWT( String.valueOf(usuarioLogin.getId()), usuarioBean.getLogin(), usuarioLogin.getTipousuario().getNombre() );
        }
        else{
            throw new UnauthorizationException("Usuario o contraseña errónea");
        }    
    }

    //check con JWT
    public UsuarioEntity checkJWT() {
        //String usuarioLogged = (String) servletRequest.getAttribute("usuario"); esta variable se ha puesto directamente en el findByLogin
        UsuarioEntity usuarioLogged = usuarioRepo.findByLogin( (String)servletRequest.getAttribute("usuario") );
        if (usuarioLogged != null) {
            return usuarioLogged;
        }
        else{
            throw new UnauthorizationException("No hay usuario conectado");
        }
    }

    //onlyAdmins con JWT
    public void onlyAdmins() {
        UsuarioEntity usuarioLogged = usuarioRepo.findByLogin( (String)servletRequest.getAttribute("usuario") );
        
        if (usuarioLogged == null || usuarioLogged.getTipousuario().getId() != 1) {
            throw new UnauthorizationException("Función sólo para administradores");
        }
    }

    public void onlyAdminsOrNewUser() { //MIRAR PARA QUE UN USUARIO VISITANTE SE PUEDA CREAR UN USUARIO CON TIPOUSUARIO 2
        UsuarioEntity usuarioLogged = (UsuarioEntity) servletRequest.getAttribute("usuario");
        if(usuarioLogged != null && usuarioLogged.getTipousuario().getId() != 1) {
            throw new UnauthorizationException("Accion sólo para administradores y nuevos usuarios");
        }
    }

    //onlyUsers con JWT
    public void onlyUsers() {
        UsuarioEntity usuarioLogged = usuarioRepo.findByLogin( (String)servletRequest.getAttribute("usuario") );
        if (usuarioLogged == null || usuarioLogged.getTipousuario().getId() != 2) {
            throw new UnauthorizationException("Función sólo para usuarios registrados y administradores");
        }
    }

    //onlyAdminOrOwnData con JWT
    public void OnlyAdminsOrOwnUserData(Long id) {
        UsuarioEntity usuarioLogged = usuarioRepo.findByLogin( (String)servletRequest.getAttribute("usuario") );

        if (usuarioLogged == null) {
            throw new UnauthorizationException("Sólo para administradores o consultar datos propios de usuario");
        } 
        else if(usuarioLogged.getTipousuario().getId() == 2 && usuarioLogged.getId() != id){
            throw new UnauthorizationException("Sólo puedes consultar tus propios datos");
        }
    }

    //isAdmin con JWT
    public boolean isAdmin() {
        UsuarioEntity usuarioLogged = usuarioRepo.findByLogin( (String)servletRequest.getAttribute("usuario") );

        if(usuarioLogged != null && usuarioLogged.getTipousuario().getId().equals(1L)) {
            return true;
        }
        else {
            return false;
        }
    }
}
