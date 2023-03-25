package net.ausiasmarch.cineServerJWT.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.cineServerJWT.entities.TarifaEntity;
import net.ausiasmarch.cineServerJWT.exceptions.ResourceNotFound;
import net.ausiasmarch.cineServerJWT.exceptions.ResourceNotModified;
import net.ausiasmarch.cineServerJWT.helper.ValidationHelper;
import net.ausiasmarch.cineServerJWT.repository.TarifaRepository;

@Service
public class TarifaService {

    @Autowired
    AuthService authService;

    @Autowired
    TarifaRepository tarifaRepo;

    public void validateID(Long id) {
        if (!tarifaRepo.existsById(id)) {
            throw new ResourceNotFound("No existe tarifa con id  " + id);
        }
    }

    public void validate(TarifaEntity tarifaEntity) {
        ValidationHelper.validateStringLength(tarifaEntity.getNombre(), 1, 15, "Nombre no válido");
        ValidationHelper.validateDoubleRange(tarifaEntity.getPrecio(), 0, 50.0, "Precio no válido");
    }

    public TarifaEntity get(Long id) {
        authService.onlyAdmins();
        validateID(id);
        return tarifaRepo.findById(id).get(); 
    }

    public Long create (TarifaEntity newTarifa) {
        authService.onlyAdmins();
        validate(newTarifa);
        newTarifa.setId(0L);
        return tarifaRepo.save(newTarifa).getId();
    }

    @Transactional
    public Long update(TarifaEntity updatedTarifa) {
        authService.onlyAdmins();
        validateID(updatedTarifa.getId());
        validate(updatedTarifa);

        TarifaEntity actualTarifa = tarifaRepo.findById(updatedTarifa.getId()).get();
        actualTarifa.setNombre(updatedTarifa.getNombre());
        actualTarifa.setPrecio(updatedTarifa.getPrecio());
        return tarifaRepo.save(actualTarifa).getId();
    }
    
    public Long delete(Long id) {
        authService.onlyAdmins();
        validateID(id);
        tarifaRepo.deleteById(id);

        if (tarifaRepo.existsById(id)) {
            throw new ResourceNotModified("No se puede borrar el registro con ID "+id); 
        } else {
            return id;
        }
    }

    public Long count() {
        //authService.onlyAdmins();
        return tarifaRepo.count();
    }

    public Page<TarifaEntity> getPage(Pageable pageable, String filter) {

        if(filter == null || filter.length() == 0) {
            return tarifaRepo.findAll(pageable);
        } else {
            return tarifaRepo.findByNombreIgnoreCaseContaining( filter, pageable);
        }
        
        
    }
}
