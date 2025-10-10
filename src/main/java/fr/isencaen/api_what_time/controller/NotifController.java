package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.NotifDto;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.service.NotifService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotifController {
    private final NotifService notifService;

    public NotifController(NotifService notifService) {
        this.notifService = notifService;
    }

    @GetMapping("v1/notif")
    public Page<NotifDto> getAllNotifs(
            @ParameterObject Pageable pageable
    ){

         return notifService.getAllNotifs(pageable).map(NotifDto::of);
    }

    @GetMapping("v1/notif/{idNotif}")
    public NotifDto getNotif(@PathVariable int idNotif){
        return NotifDto.of(notifService.getNotif(idNotif));
    }

}
