package fr.isencaen.api_what_time.service.Model;

import java.time.LocalDateTime;

import fr.isencaen.api_what_time.repository.Entity.Notif;

public record NotifModel(
        int idNotif,
        int idEvent,
        boolean modify
) {
    public static NotifModel of(Notif notif){
        return new NotifModel(
                notif.getIdNotif(),
                notif.getIdEvent(),
                notif.getModify()
        );
    }
}
