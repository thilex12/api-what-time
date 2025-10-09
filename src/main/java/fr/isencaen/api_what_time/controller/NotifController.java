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
    public List<NotifDto> getAllNotif(){
         return notifService.getAllNotifs().stream().map(NotifDto::of).toList();
    }

}
