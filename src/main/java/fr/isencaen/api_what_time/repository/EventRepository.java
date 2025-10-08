package fr.isencaen.api_what_time.repository;

import fr.isencaen.api_what_time.repository.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
