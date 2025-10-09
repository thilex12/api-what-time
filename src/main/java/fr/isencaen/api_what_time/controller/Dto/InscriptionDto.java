package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.EventModel;
import fr.isencaen.api_what_time.service.Model.InscriptionModel;

public record InscriptionDto(
        int id,
        AccountDto account,
        EventDto event
) {
    public static InscriptionDto of(InscriptionModel inscriptionModel) {
        return new InscriptionDto(
                inscriptionModel.id(),
                AccountDto.of(AccountModel.of(inscriptionModel.account())),
                EventDto.of(EventModel.of(inscriptionModel.event()))
        );
    }

}
