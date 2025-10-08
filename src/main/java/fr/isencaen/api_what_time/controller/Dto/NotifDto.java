package fr.isencaen.api_what_time.controller.dto;
import java.time.LocalDateTime;
import fr.isencaen.api_what_time.service.model.NotifModel;

public record NotifDto(
        int idNotif,
        int idEvent,
        LocalDateTime dateSend
){
    public static NotifDto of(NotifModel notifModel){
        return new NotifDto(
                notifModel.idNotif(),
                notifModel.idEvent(),
                notifModel.dateSend()
        );
    }
}
