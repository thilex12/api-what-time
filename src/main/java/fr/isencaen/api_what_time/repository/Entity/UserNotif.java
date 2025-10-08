package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.time.LocalDateTime;

@Table(name="User-Notif")
public class UserNotif {
    @Lazy
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false)
    private int idUser;

    @OneToMany
    @JoinColumn(name = "id_notif", referencedColumnName = "id_notif", nullable = false)
    private int idNotif;
    private boolean read;
    private LocalDateTime readDate;


}