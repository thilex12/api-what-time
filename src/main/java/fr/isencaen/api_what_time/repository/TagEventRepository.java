package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.TagEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagEventRepository extends JpaRepository<TagEvent, Integer>, JpaSpecificationExecutor<TagEvent> {
//    Page<Tag> findAll(Specification<Tag> spec, Pageable pageable);
}
