package net.ausiasmarch.cineServerJWT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServerJWT.entities.TipoUsuarioEntity;
import net.ausiasmarch.cineServerJWT.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServerJWT.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {

    @Autowired
    AuthService authService;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepo;

    public void validate(Long id) {
        if (!tipoUsuarioRepo.existsById(id)) {
            throw new ResourceNotFound("No existe este tipo de usuario");
        }
    }

    //GET Method
    public TipoUsuarioEntity get(Long id) {
        validate(id);
        return tipoUsuarioRepo.getReferenceById(id);
    }


    public Long count() {
        return tipoUsuarioRepo.count();
    }

    public Page<TipoUsuarioEntity> getPage(Pageable pageable) {

        Page<TipoUsuarioEntity> page = tipoUsuarioRepo.findAll(pageable);

        return page;
    } 

    public Long create(TipoUsuarioEntity newTipoUsuario) {
        authService.onlyAdmins();
        newTipoUsuario.setId(0L);
        return tipoUsuarioRepo.save(newTipoUsuario).getId();
    }

    public Long delete(Long id) {
        tipoUsuarioRepo.deleteById(id);
        return id;
    }

    public Long update(TipoUsuarioEntity tipoUsuarioEntity) {
        return tipoUsuarioRepo.save(tipoUsuarioEntity).getId();
    }
}
