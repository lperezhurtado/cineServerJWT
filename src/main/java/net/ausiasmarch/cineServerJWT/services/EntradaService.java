package net.ausiasmarch.cineServerJWT.services;

import javax.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServerJWT.entities.EntradaEntity;
import net.ausiasmarch.cineServerJWT.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServerJWT.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServerJWT.repository.EntradaRepository;
@Service
public class EntradaService {
    @Autowired
    AuthService authService;
    @Autowired
    EntradaRepository entradaRepo;

    @Autowired
    SalaService salaService;

    //@Autowired
    //SesionService sesionService;

    public void validateID(Long id) {
        if (!entradaRepo.existsById(id)) {
            throw new ResourceNotFound("No se encuentra butaca con id  " + id);
        }
    }

    public void validateCoor(int x, int y, int fila, int col) {
    
        //entrada.getSesion().getId().get;
    }

    public void validate(EntradaEntity entradaEntity) {

    }

    public Long count() {
        return entradaRepo.count();
    }

    public Page<EntradaEntity> getPage(Pageable pageable, Long id_sesion) {
        if (id_sesion == null) {
            return entradaRepo.findAll(pageable);
        } else {
            return entradaRepo.findBySesionId(id_sesion, pageable);
        }
    }

    public List<EntradaEntity> getList(Long id_sesion) {
        if (id_sesion == null) {
            return entradaRepo.findAll();
        } else {
            return entradaRepo.findBySesionId(id_sesion);
        }
    }

    public EntradaEntity get(Long id) {
        validateID(id);
        return entradaRepo.getReferenceById(id);
    }

    public Long create(EntradaEntity newEntrada) {
        authService.onlyAdmins();
        newEntrada.setId(0L);
        return entradaRepo.save(newEntrada).getId();
    }

    @Transactional
    public Long update(EntradaEntity updatedEntrada) {
        validateID(updatedEntrada.getId());

        EntradaEntity actualEntrada = entradaRepo.getReferenceById(updatedEntrada.getId());
        actualEntrada.setEjeX(updatedEntrada.getEjeX());
        actualEntrada.setEjeY(updatedEntrada.getEjeY());
        actualEntrada.setLibre(updatedEntrada.isLibre());
        actualEntrada.setSesion(updatedEntrada.getSesion());

        return entradaRepo.save(actualEntrada).getId();
    }

    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        entradaRepo.deleteById(id);

        if(entradaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id);
        } else {
            return id;
        }
    }
}