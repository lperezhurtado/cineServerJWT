package net.ausiasmarch.cineServerJWT.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.cineServerJWT.entities.GeneroEntity;

public interface GeneroRepository extends JpaRepository<GeneroEntity, Long> {
    
    public Page<GeneroEntity> findByNombreIgnoreCaseContaining(String filter, Pageable pageable);

}
