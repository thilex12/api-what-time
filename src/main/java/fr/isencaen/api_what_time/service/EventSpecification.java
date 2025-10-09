package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Allow;
import fr.isencaen.api_what_time.repository.Entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class EventSpecification {
    public static Specification<Event> findByName(Optional<String> name) {
        if (name.isPresent()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name.get() + "%");
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByBeforeDate(Optional<LocalDateTime> beforeDate) {
        if (beforeDate.isPresent()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), beforeDate.get());
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByAfterDate(Optional<LocalDateTime> afterDate) {
        if (afterDate.isPresent()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), afterDate.get());
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByLocation(Optional<String> location) {
        if (location.isPresent()) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location").get("name"), "%" + location.get() + "%");
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByTag(List<Integer> tags) {
        if (tags != null && !tags.isEmpty()) {
            return (root, query, criteriaBuilder) -> root.get("tags").get("id").in(tags);
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByIsArchivedFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isArchived"));
    }


    public static Specification<Event> canUserSeeEvent(int id_user) {
        return (root, query, criteriaBuilder) -> {
            var subquery = query.subquery(Long.class);
            var allowRoot = subquery.from(Allow.class);
            subquery.select(allowRoot.get("id"))
                    .where(
                            criteriaBuilder.equal(allowRoot.get("event").get("id"), root.get("id")),
                            criteriaBuilder.equal(allowRoot.get("account").get("id"), id_user)
                    );
            return criteriaBuilder.or(
                    criteriaBuilder.isTrue(root.get("visibility")),
                    criteriaBuilder.and(
                            criteriaBuilder.isFalse(root.get("visibility")),
                            criteriaBuilder.exists(subquery)
                    )
            );
        };
    }

}
