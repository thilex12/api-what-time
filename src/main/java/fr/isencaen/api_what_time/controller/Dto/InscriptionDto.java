package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.EventModel;
import fr.isencaen.api_what_time.service.Model.InscriptionModel;

public record InscriptionDto(
        int id,
        int accountId,
        int eventId
) {
    public static InscriptionDto of(InscriptionModel inscriptionModel) {
        return new InscriptionDto(
                inscriptionModel.id(),
                inscriptionModel.accountId(),
                inscriptionModel.eventId()
        );
    }

}
