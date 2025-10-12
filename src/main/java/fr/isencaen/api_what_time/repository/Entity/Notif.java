package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.util.List;
import java.time.LocalDateTime;


@Entity
@Table(name="notif")
public class Notif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNotif;
    private int modify;
    private LocalDateTime creationDate;
    private boolean read;
    private boolean archive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", referencedColumnName = "id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvent", referencedColumnName = "id", nullable = false)
    private Event event;

    @Column(name = "idEvent", updatable = false, insertable = false)
    private Integer idEvent;

    public Notif(){}

    public Notif(Event event, int modify, LocalDateTime creationDate, boolean read, boolean archive,
                 Account account){
        this.event = event;
        this.modify = modify;
        this.creationDate = creationDate;
        this.read = read;
        this.archive = archive;
        this.account = account;
    }

    public Notif(int idNotif, Event event, int modify, LocalDateTime creationDate, Account account, boolean read,
                 boolean archive){
        this.idNotif = idNotif;
        this.event = event;
        this.modify = modify;
        this.creationDate = creationDate;
        this.read = read;
        this.archive = archive;
        this.account = account;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public Account getAccount() {
        return account;
    }

    public Event getEvent() {
        return event;
    }

    public int getIdNotif() {
        return idNotif;
    }

    public int getModify() {
        return modify;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public boolean isRead() {
        return read;
    }

    public boolean isArchive() {
        return archive;
    }

    public Integer getIdEvent() {
        return event.getId();
    }

    public void setIdEvent(Integer idEvent) {
        event.setId(idEvent);
    }
}
