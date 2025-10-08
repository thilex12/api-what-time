package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Event;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class EventSpecification {
    public static Specification<Event> findByName(Optional<String> name){
        if (name.isPresent()){
//            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name.get());
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name.get() + "%");
//            return (root, query, criteriaBuilder) -> criteriaBuilder.like();
        } else {
            return (root, query, criteriaBuilder) -> null;
        }
    }



//    public Specification<Event> getSpecification(EventSpecification specification) {}
    public static Specification<Event> findByName(String name){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }
}
