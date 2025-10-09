package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.NotifDto;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.service.NotifService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotifController {
    private final NotifService notifService;

    public NotifController(NotifService notifService) {
        this.notifService = notifService;
    }

    @GetMapping("v1/notif")
    public List<NotifDto> getAllNotifs(){
         return notifService.getAllNotifs().stream().map(NotifDto::of).toList();
    }

    @GetMapping("v1/notif/{idNotif}")
    public NotifDto getNotif(@PathVariable int idNotif){
        return NotifDto.of(notifService.getNotif(idNotif));
    }

}
