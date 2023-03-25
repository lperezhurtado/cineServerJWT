package net.ausiasmarch.cineServerJWT.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServerJWT.entities.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long>{
    
    Page<FacturaEntity> findByUsuarioId(Long id_usuario, Pageable pageable);

}
