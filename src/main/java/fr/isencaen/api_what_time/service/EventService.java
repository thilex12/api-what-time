package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.AllowRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Allow;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.Entity.Inscription;
import fr.isencaen.api_what_time.repository.EventRepository;
import fr.isencaen.api_what_time.repository.InscriptionRepository;
import fr.isencaen.api_what_time.service.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    AllowRepository allowRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InscriptionRepository inscriptionRepository;

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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
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

    //    @Cacheable(cacheNames = "events")
    @Transactional
    public EventModel getEventById(int id) {
        Event eventEntity = eventRepository.findById(id).orElseThrow();
        if (eventEntity.isArchived()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        EventModel event = EventModel.of(eventEntity);
        return event;
    }

    @Transactional
    public EventModel createEvent(CreateEventModel createEventModel) {

        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
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
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
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
        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow();
        var account = accountRepository.findById(accountId).orElseThrow();
        if (event.getId_owner() != id_owner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the event owner can add allowed accounts");
        }
        if (event.isVisibility()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only private events can have allowed accounts list");
        }
        Allow allow = new Allow(account, event);
        allowRepository.save(allow);
        event.getAllowedAccountsList().add(allow);
        eventRepository.save(event);
    }

    @Transactional
    public void joinEvent(int eventId) {

        int id_user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_user = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow();
        var account = accountRepository.findById(id_user).orElseThrow();

        if (event.getInscriptionsList().stream().anyMatch(inscriptions -> inscriptions.getAccount().getId() == id_user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already registered to the event");
        }
        if (!event.isVisibility()) {
            if (event.getAllowedAccountsList().stream().noneMatch(allow -> allow.getAccount().getId() == id_user)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to register to this event");
            }
        }
        Inscription inscription = new Inscription(account, event);
        inscriptionRepository.save(inscription);
    }

    @Transactional
    public void leaveEvent(int eventId) {

        int id_user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_user = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow();
//        var account = accountRepository.findById(id_user);

        Inscription inscription = event.getInscriptionsList().stream()
                .filter(inscriptions -> inscriptions.getAccount().getId() == id_user)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not registered to the event"));

        event.getInscriptionsList().remove(inscription);
        inscriptionRepository.delete(inscription);
    }

    public void removeAccountFromAllowedList(int eventId, int accountId) {
        int id_owner;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_owner = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow();
//        var account = accountRepository.findById(accountId)
        if (event.getId_owner() != id_owner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the event owner can remove allowed accounts");
        }
        Allow allow = event.getAllowedAccountsList().stream()
                .filter(allowfilter -> allowfilter.getAccount().getId() == accountId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account is not in the allowed accounts list"));
        event.getAllowedAccountsList().remove(allow);
        allowRepository.delete(allow);
    }
}
