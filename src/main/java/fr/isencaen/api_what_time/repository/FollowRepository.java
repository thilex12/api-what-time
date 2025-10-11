package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.FollowTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowTag, Integer>, JpaSpecificationExecutor<FollowTag> {
    Page<FollowTag> findAll(Specification<FollowTag> spec, Pageable pageable);
    List<FollowTag> findAllByAccount(Account account);
}