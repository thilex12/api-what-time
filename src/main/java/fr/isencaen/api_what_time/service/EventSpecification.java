package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.Entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Optional;

public class EventSpecification {
    public static Specification<Event> findByName(Optional<String> name){
        if (name.isPresent()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name.get() + "%");
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }
    public static Specification<Event> findByBeforeDate(Optional<LocalDateTime> beforeDate){
        if (beforeDate.isPresent()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), beforeDate.get());
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }
    public static Specification<Event> findByAfterDate(Optional<LocalDateTime> afterDate){
        if (afterDate.isPresent()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), afterDate.get());
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByLocation(Optional<String> location){
        if (location.isPresent()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location").get("name"), "%" + location.get() + "%");
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }

    public static Specification<Event> findByTag(Optional<Tag> tag){
        if (tag.isPresent()){
            return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("tag").get("id")).value(tag.get().getId());
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }




//    public Specification<Event> getSpecification(EventSpecification specification) {}
//    public static Specification<Event> findByName(String name){
//        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
//    }
}
