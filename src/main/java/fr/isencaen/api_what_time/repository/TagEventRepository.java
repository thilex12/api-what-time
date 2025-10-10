package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.TagEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagEventRepository extends JpaRepository<TagEvent, Integer>, JpaSpecificationExecutor<TagEvent> {
    Page<TagEvent> findAll(Specification<TagEvent> spec, Pageable pageable);
}
