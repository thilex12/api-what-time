package fr.isencaen.api_what_time.controller;


import fr.isencaen.api_what_time.controller.Dto.*;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.EventService;
import fr.isencaen.api_what_time.service.Model.CreateEventModel;
import fr.isencaen.api_what_time.service.Model.EventFilterModel;
import fr.isencaen.api_what_time.service.Model.UpdateEventModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private AccountService accountService;


//    @GetMapping("v1/events")

    @Operation(summary = "Retounre la liste des évenements", description = "Permet de filtrer les évenements par nom, date, lieu et tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des évenements retournée",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content),
    })
    @GetMapping("v1/events")
    public Page<EventDto> getEvents(
            @ParameterObject Pageable pageable,
            @ParameterObject EventFilterDto eventFilter
    ) {
//        Si l'utilisateur est juste USER on fait getEvents, si ADMIN on fait getAllEvents

        return eventService.getEvents(
                pageable,
                EventFilterModel.of(eventFilter)
        ).map(EventDto::of);
    }

    @GetMapping("v1/admin-events")
    public Page<EventDtoAdmin> getAdminEvents(
            @ParameterObject Pageable pageable
//            @ParameterObject EventFilterDto eventFilter
    ) {
        return eventService.getAllEvents(
                pageable
//                EventFilterModel.of(eventFilter)
        ).map(EventDtoAdmin::of);
    }

    @GetMapping("v1/admin-events/{id}")
    public EventDto getAdminEventById(
            @PathVariable int id
    ) {
        try {
            return EventDto.of(eventService.getAnyEventById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Évenement non trouvé");
        }
    }

    @Operation(summary = "Retourne un évenement par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Évenement retourné",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "404", description = "Évenement non trouvé",
                    content = @Content),
    })

    @GetMapping("v1/events/{id}")
    public EventDto getEventById(
            @PathVariable int id
    ) {
        try {
            return EventDto.of(eventService.getEventById(id));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Évenement non trouvé");
        }
    }


    @Operation(summary = "Crée un évenement")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Évenement créé",
//                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateEventDto.class))}),
//            @ApiResponse(responseCode = "400", description = "Requête invalide",
//                    content = @Content),
//    })
    @PostMapping(value = "v1/events", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto createEvent(
//            @RequestBody CreateEventDto createEventDto
            @Valid @RequestBody CreateEventDto createEventDto
    ) {
        return EventDto.of(eventService.createEvent(CreateEventModel.of(createEventDto)));
//        return TagDto.of(tagService.createTag(CreateTagModel.of(tag)));

    }

    @Operation(summary = "Supprime un évenement par son ID")
    @DeleteMapping("v1/events/{id}")
    public void deleteEvent(
            @PathVariable int id
    ) {
        eventService.deleteEvent(id);
    }

    @Operation(summary = "Met à jour un évenement par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Évenement mis à jour",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EventDto.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Évenement non trouvé",
                    content = @Content),
    })
    @PutMapping("v1/events/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto updateEvent(
            @PathVariable int id,
            @Valid @RequestBody UpdateEventDto updateEventDto
    ) {
        return EventDto.of(eventService.updateEvent(id, UpdateEventModel.of(updateEventDto)));
    }

    @Operation(summary = "Ajoute un compte à la liste des comptes autorisés à voir un évenement privé")
    @PostMapping("v1/events/{eventId}/accounts/{accountId}/allow")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAccountToAllowedList(
            @PathVariable int eventId,
            @PathVariable int accountId
    ) {
        eventService.addAccountToAllowedList(eventId, accountId);
    }

    @DeleteMapping("v1/events/{eventId}/accounts/{accountId}/delete")
    public void removeAccountFromAllowedList(
            @PathVariable int eventId,
            @PathVariable int accountId
    ) {
        eventService.removeAccountFromAllowedList(eventId, accountId);
    }


    @PostMapping("v1/events/{eventId}/join")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinEvent(
            @PathVariable int eventId
    ) {
        eventService.joinEvent(eventId);
    }


    @DeleteMapping("v1/events/{eventId}/leave")
    public void leaveEvent(
            @PathVariable int eventId
    ) {
        eventService.leaveEvent(eventId);
    }

}
