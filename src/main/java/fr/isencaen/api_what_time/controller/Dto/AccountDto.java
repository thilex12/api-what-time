package fr.isencaen.api_what_time.controller.Dto;

import fr.isencaen.api_what_time.service.Model.AccountModel;

import java.util.List;

public record AccountDto(
        int id,
        String name,
        String surname,
        String mail,
        List<TagDto> tags,
        List<InscriptionDto> inscriptions
) {
    public static AccountDto of(AccountModel account) {
        return new AccountDto(
                account.id(),
                account.name(),
                account.surname(),
                account.mail(),
                account.tags().stream().map(TagDto::of).toList(),
                account.inscriptions().stream().map(InscriptionDto::of).toList()
        );
    }
}
