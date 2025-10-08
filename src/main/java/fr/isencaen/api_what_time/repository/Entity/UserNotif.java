package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name="User-Notif")
public class UserNotif {
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private int idUser;

    @ManyToOne
    @JoinColumn(name = "id_notif", nullable = false)
    private int idNotif;
    private boolean read;


}