package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Inscription;
import fr.isencaen.api_what_time.repository.Entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    Page<Tag> findAll(Specification<Tag> spec, Pageable pageable);
}
