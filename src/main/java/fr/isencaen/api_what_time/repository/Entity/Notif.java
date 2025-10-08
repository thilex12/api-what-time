package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="notif")
public class Notif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNotif;
    private int idEvent;
    private boolean modify;

    @OneToMany
    private List<UserNotif> userNotifs;

    public Notif(){}

    public Notif(int idEvent, boolean modify){
        this.idEvent = idEvent;
        this.modify = modify;
    }

    public Notif(int idNotif, int idEvent, boolean modify){
        this.idNotif = idNotif;
        this.idEvent = idEvent;
        this.modify = modify;
    }

    public int getIdNotif() {
        return idNotif;
    }

    public boolean getModify() {
        return modify;
    }

    public int getIdEvent() {
        return idEvent;
    }


}
