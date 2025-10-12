package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.NotifDto;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.service.Model.NotifModel;
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

    @GetMapping("v1/notifs")
    public Page<NotifDto> getAllNotifs(
            @ParameterObject Pageable pageable
    ){

         return notifService.getAllNotifs(pageable).map(NotifDto::of);
    }

    @GetMapping("v1/notifs/{idNotif}")
    public NotifDto getNotif(@PathVariable Integer idNotif){
        NotifModel notif = notifService.getNotif(idNotif);
        if (notif == null) return null;
        return NotifDto.of(notif);
    }

    @DeleteMapping ("v1/notifs/{idNotif}")
    public NotifDto deleteNotif(@PathVariable int idNotif){
        NotifModel notif = notifService.deleteNotif(idNotif);
        if (notif == null) return null;
        return NotifDto.of(notif);
    }
}
