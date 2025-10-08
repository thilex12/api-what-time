package fr.isencaen.api_what_time.repository.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "allow")
public class Allow {


    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_event", referencedColumnName = "id")
    private Event event;
}
