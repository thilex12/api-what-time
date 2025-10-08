package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.EventRepository;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventFilterModel;
import fr.isencaen.api_what_time.service.Model.EventModel;
import jakarta.transaction.Transactional;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;

    public Page<EventModel> getEvents(
            Pageable pageable,
            EventFilterModel eventFilterModel
    ) {

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth.isAuthenticated() && auth.getPrincipal() instanceof User user) {
//            user.getUsername();
//        }

        return eventRepository.findAll(
                EventSpecification.findByName(eventFilterModel.name())
                        .and(EventSpecification.findByBeforeDate(eventFilterModel.beforeDate()))
                        .and(EventSpecification.findByAfterDate(eventFilterModel.afterDate()))
                        .and(EventSpecification.findByLocation(eventFilterModel.location()))
                        .and(EventSpecification.findByTag(eventFilterModel.tag()))
                        ,
                pageable
        ).map(EventModel::of);
    }





    @Transactional
    public EventModel createEvent(CreateEventModel createEventModel) {

        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        }
        else {
            throw new RuntimeException("User not authenticated");
        }

        return EventModel.of(eventRepository.save(
                new Event(
                        id_owner,
                        createEventModel.name(),
                        createEventModel.description(),
                        LocalDateTime.now(),
                        createEventModel.startDate(),
                        createEventModel.endDate(),
                        createEventModel.location(),
                        createEventModel.visibility()

                )
        ));
    }


}
