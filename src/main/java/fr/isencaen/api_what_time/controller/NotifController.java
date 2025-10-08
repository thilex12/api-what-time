package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.NotifDto;
import fr.isencaen.api_what_time.repository.Entity.Notif;
import fr.isencaen.api_what_time.service.NotifService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotifController {
    private final NotifService notifService;

    public NotifController(NotifService notifService) {
        this.notifService = notifService;
    }

     @GetMapping("api/v1/notification")
    public List<NotifDto> getNotif(){
         return notifService.getNotif().stream().map(NotifDto::of).toList();
     }
}
