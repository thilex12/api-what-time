package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Allow;
import fr.isencaen.api_what_time.repository.Entity.Inscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Integer>, JpaSpecificationExecutor<Inscription> {
    Page<Inscription> findAll(Specification<Inscription> spec, Pageable pageable);
}
