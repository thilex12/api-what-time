package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.*;
import fr.isencaen.api_what_time.repository.Entity.*;
import fr.isencaen.api_what_time.service.Model.*;
import fr.isencaen.api_what_time.service.NotifService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    NotifService notifService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    AllowRepository allowRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    InscriptionRepository inscriptionRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagEventService tagEventService;
    @Autowired
    LocationRepository locationRepository;

//    @Cacheable(cacheNames = "events")
//    @Transactional
//    public Event getEventById(int id) {
//        return EventModel.of(eventRepository.findById(id).orElseThrow());
//    }

    public Page<EventModelAdmin> getAllEvents(Pageable pageable) {

        int id_user;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            Account user_account = user.getAccount();
            id_user = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        
        return eventRepository.findAll(pageable).map(EventModelAdmin::of);
    }

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

        LocalDateTime createddate = LocalDateTime.now();
        Account user_account;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            user_account = user.getAccount();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Location location = null;
        if (createEventModel.locationId() != null) {
            location = locationRepository.findById(createEventModel.locationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found"));
        }

        Event event = new Event(
                user_account.getId(),
                createEventModel.name(),
                createEventModel.description(),
                LocalDateTime.now(),
                createEventModel.startDate(),
                createEventModel.endDate(),
                location,
                createEventModel.visibility(),
                false
        );
        EventModel eventModel = EventModel.of(eventRepository.save(event));

        notifService.createNotifDel(event, user_account, LocalDateTime.now());
//        return EventModel.of(eventRepository.save(event));

        if (createEventModel.tags() != null && !createEventModel.tags().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(createEventModel.tags());
            if (tags.size() != createEventModel.tags().size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more tags do not exist");
            }
            for (Tag tag : tags) {
                TagEvent tagEvent = new TagEvent(tag, event);
                tagEventService.createTagEvent(TagEventModel.of(tagEvent));
            }
        }
        return eventModel;
    }

    //    @CacheEvict(cacheNames = "events")
    @Transactional
    public void deleteEvent(int id) {
        Account user_account;
        Event event = eventRepository.findById(id).orElseThrow();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            user_account = user.getAccount();
        } else {
            throw new RuntimeException("User not authenticated");
        }
        if (event.getId_owner() != user_account.getId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to delete");
        }

        notifService.createNotifDel(event, user_account, LocalDateTime.now());
        event.setArchived(true);
    }

    @Transactional
    public EventModel updateEvent(int id, UpdateEventModel updateEventModel) {

        Event event = eventRepository.findById(id).orElseThrow();
        Account user_account;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            user_account = user.getAccount();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        // Correction : vérification sur le propriétaire de l'événement
        if (event.getId_owner() != user_account.getId())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to update");

        notifService.createNotifModif(event, user_account, LocalDateTime.now());

        event.setName(updateEventModel.name());
        event.setDescription(updateEventModel.description());
        event.setStartDate(updateEventModel.startDate());
        event.setEndDate(updateEventModel.endDate());
        // Correction : gestion propre de la localisation
        if (updateEventModel.locationId() != null) {
            event.setLocation(locationRepository.findById(updateEventModel.locationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found")));
        } else {
            event.setLocation(null);
        }

        // Mise à jour des tags
        List<TagEvent> currentTags = event.getTagsList();
        List<Integer> newTagIds = updateEventModel.tags();

        if (updateEventModel.tags() == null || updateEventModel.tags().isEmpty()) {
            List<TagEvent> toRemove = new ArrayList<>(currentTags);
            for (TagEvent tagEvent : toRemove) {
                event.getTagsList().remove(tagEvent);
                tagEventService.deleteTagEvent(tagEvent.getId());
            }
        } else {
            List<TagEvent> toRemove = new ArrayList<>();
            for (TagEvent tagEvent : currentTags) {
                if (!newTagIds.contains(tagEvent.getTag().getId())) {
                    toRemove.add(tagEvent);
                }
            }
            for (TagEvent tagEvent : toRemove) {
                event.getTagsList().remove(tagEvent);
                tagEventService.deleteTagEvent(tagEvent.getId());
            }
            // Ajouter les nouveaux tags qui ne sont pas déjà présents
            List<Integer> currentTagIds = currentTags.stream()
                    .map(tagEvent -> tagEvent.getTag().getId())
                    .toList();
            for (Integer tagId : newTagIds) {
                if (!currentTagIds.contains(tagId)) {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag not found"));
                    TagEvent tagEvent = new TagEvent(tag, event);
                    tagEventService.createTagEvent(TagEventModel.of(tagEvent));
                }
            }
        }

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
        Account user_account;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user) {
            user_account = user.getAccount();
            id_user = user_account.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        Event event = eventRepository.findById(eventId).orElseThrow();

        Inscription inscription = inscriptionRepository.findAll().stream()
                .filter(inscriptions -> inscriptions.getAccount().getId() == id_user && inscriptions.getEvent().getId() == eventId)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not registered to the event"));

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

    public EventModel getAnyEventById(int id) {
        Event eventEntity = eventRepository.findById(id).orElseThrow();
        EventModel event = EventModel.of(eventEntity);
        return event;
    }

    @Transactional
    public EventModel updateAnyEvent(int id, UpdateEventAdminModel of) {
        Event event = eventRepository.findById(id).orElseThrow();

        event.setName(of.name());
        event.setDescription(of.description());
        event.setStartDate(of.startDate());
        event.setEndDate(of.endDate());
        // Correction : gestion propre de la localisation
        if (of.locationId() != null) {
            event.setLocation(locationRepository.findById(of.locationId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location not found")));
        } else {
            event.setLocation(null);
        }

        // Mise à jour des tags
        List<TagEvent> currentTags = event.getTagsList();
        List<Integer> newTagIds = of.tags();

        if (of.tags() == null || of.tags().isEmpty()) {
            List<TagEvent> toRemove = new ArrayList<>(currentTags);
            for (TagEvent tagEvent : toRemove) {
                event.getTagsList().remove(tagEvent);
                tagEventService.deleteTagEvent(tagEvent.getId());
            }
        } else {
            List<TagEvent> toRemove = new ArrayList<>();
            for (TagEvent tagEvent : currentTags) {
                if (!newTagIds.contains(tagEvent.getTag().getId())) {
                    toRemove.add(tagEvent);
                }
            }
            for (TagEvent tagEvent : toRemove) {
                event.getTagsList().remove(tagEvent);
                tagEventService.deleteTagEvent(tagEvent.getId());
            }
            // Ajouter les nouveaux tags qui ne sont pas déjà présents
            List<Integer> currentTagIds = currentTags.stream()
                    .map(tagEvent -> tagEvent.getTag().getId())
                    .toList();
            for (Integer tagId : newTagIds) {
                if (!currentTagIds.contains(tagId)) {
                    Tag tag = tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tag not found"));
                    TagEvent tagEvent = new TagEvent(tag, event);
                    tagEventService.createTagEvent(TagEventModel.of(tagEvent));
                }
            }
        }

        event.setVisibility(of.visibility());
        event.setArchived(of.archived());
        return EventModel.of(event);
    }
}
