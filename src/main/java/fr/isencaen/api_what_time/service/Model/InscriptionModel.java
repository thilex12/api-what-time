package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.Event;
import fr.isencaen.api_what_time.repository.Entity.Inscription;
import fr.isencaen.api_what_time.repository.Entity.Tag;

public record InscriptionModel (
            int id,
            int accountId,
            int eventId
    ) {
        public static InscriptionModel of(Inscription inscription) {
            return new InscriptionModel(
                    inscription.getId(),
                    inscription.getAccount().getId(),
                    inscription.getEvent().getId()
            );
        }
    }
