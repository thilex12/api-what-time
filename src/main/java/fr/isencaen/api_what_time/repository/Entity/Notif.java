package fr.isencaen.api_what_time.repository.entity;

import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Notif {
    @Id
    private int idNotif;
    private int idEvent;
    private LocalDateTime dateSend;
}
