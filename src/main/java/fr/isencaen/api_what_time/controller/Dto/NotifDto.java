package fr.isencaen.api_what_time.controller.Dto;
import java.time.LocalDateTime;

import fr.isencaen.api_what_time.service.Model.NotifModel;

public record NotifDto(
        int idNotif,
        int idEvent,
        int modify,
        boolean read,
        LocalDateTime dateCreaNotif
){
    public static NotifDto of(NotifModel notifModel){
        return new NotifDto(
                notifModel.idNotif(),
                notifModel.idEvent(),
                notifModel.modify(),
                notifModel.read(),
                notifModel.dateCreaNotif()
        );
    }
}
