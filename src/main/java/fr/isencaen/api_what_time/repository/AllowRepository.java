package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Allow;
import fr.isencaen.api_what_time.repository.Entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AllowRepository extends JpaRepository<Allow, Integer>, JpaSpecificationExecutor<Allow> {
    Page<Allow> findAll(Specification<Allow> spec, Pageable pageable);
}
