package net.ausiasmarch.cineServerJWT.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServerJWT.entities.TarifaEntity;

public interface TarifaRepository extends JpaRepository<TarifaEntity, Long> {
    
    Page<TarifaEntity> findByNombreIgnoreCaseContaining(String filter, Pageable pageable);

}
