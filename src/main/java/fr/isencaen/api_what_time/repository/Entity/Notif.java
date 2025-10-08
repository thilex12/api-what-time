package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="notif")
public class Notif {
    @Id
    private int idNotif;
    private int idEvent;
    private LocalDateTime dateSend;

    public Notif(int idNotif, int idEvent, LocalDateTime dateSend){
        this.idNotif = idNotif;
        this.idEvent = idEvent;
        this.dateSend = dateSend;
    }


}
