package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.AllowRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Allow;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.EventRepository;
import fr.isencaen.api_what_time.service.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AllowRepository allowRepository;
    @Autowired
    AccountRepository accountRepository;

//    @Cacheable(cacheNames = "events")
//    @Transactional
//    public Event getEventById(int id) {
//        return EventModel.of(eventRepository.findById(id).orElseThrow());
//    }

    public Page<EventModel> getEvents(
            Pageable pageable,
            EventFilterModel eventFilterModel
    ) {
        int id_user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_user = user_account.getId();
        } else {
            throw new RuntimeException("User not authenticated");
        }

        return eventRepository.findAll(
                EventSpecification.findByName(eventFilterModel.name())
                        .and(EventSpecification.findByBeforeDate(eventFilterModel.beforeDate()))
                        .and(EventSpecification.findByAfterDate(eventFilterModel.afterDate()))
                        .and(EventSpecification.findByLocation(eventFilterModel.location()))
                        .and(EventSpecification.findByTag(eventFilterModel.tags()))
                        .and(EventSpecification.findByIsArchivedFalse())
                        .and(EventSpecification.canUserSeeEvent(id_user))
                ,
                pageable
        ).map(EventModel::of);
    }

    @Cacheable(cacheNames = "events")
    @Transactional
    public EventModel getEventById(int id) {
        return EventModel.of(eventRepository.findById(id).orElseThrow());
    }

    @Transactional
    public EventModel createEvent(CreateEventModel createEventModel) {

        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        } else {
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
                        createEventModel.visibility(),
                        false

                )
        ));
    }

    @CacheEvict(cacheNames = "events")
    @Transactional
    public void deleteEvent(int id) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setArchived(true);
    }

    @Transactional
    public EventModel updateEvent(int id, UpdateEventModel updateEventModel) {

        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        } else {
            throw new RuntimeException("User not authenticated");
        }


        Event event = eventRepository.findById(id).orElseThrow();
        event.setName(updateEventModel.name());
        event.setDescription(updateEventModel.description());
        event.setStartDate(updateEventModel.startDate());
        event.setEndDate(updateEventModel.endDate());
        event.setLocation(updateEventModel.location());
        event.setVisibility(updateEventModel.visibility());
        return EventModel.of(event);
    }

    @Transactional
    public void addAccountToAllowedList(int eventId, int accountId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        var account = accountRepository.findById(accountId).orElseThrow();
        Allow allow = new Allow(account, event);
        allowRepository.save(allow);
        event.getAllowedAccountsList().add(allow);
        eventRepository.save(event);
    }


}
