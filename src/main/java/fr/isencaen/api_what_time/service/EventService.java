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
                        .and(EventSpecification.findByLocation(eventFilterModel.location())),
                pageable
        ).map(EventModel::of);
    }





    @Transactional
    public EventModel createEvent(CreateEventModel createEventModel) {

         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
//            user.account.getId();
            Account user_account = user.getAccount();
            int id_owner = user_account.getId();
        }

        return EventModel.of(eventRepository.save(
                new Event(
                        createEventModel.id_owner(),
                        createEventModel.name(),
                        createEventModel.description(),
                        createEventModel.startDate(),
                        createEventModel.endDate(),
                        createEventModel.location(),
                        createEventModel.visibility()

                )
        ));
    }


}
