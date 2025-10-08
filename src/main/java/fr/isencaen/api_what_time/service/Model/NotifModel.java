package fr.isencaen.api_what_time.service.model;

import java.time.LocalDateTime;
import fr.isencaen.api_what_time.repository.entity.Notif;

public record NotifModel(
        int idNotif,
        int idEvent,
        LocalDateTime dateSend
) {
    public static NotifModel of(Notif notif){
        return new NotifModel(
                notif.getidNotif(),
                notif.getidEvent(),
                notif.getdateSend()
        );
    }
}
