package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Account;

import java.util.List;

public record AccountModel(
        int id,
        String name,
        String surname,
        String mail,
        String pwd,
        List<FollowTagModel> tags,
        List<InscriptionModel> inscriptions,
        boolean archived
) {
    public static AccountModel of(Account account) {
        return new AccountModel(
                account.getId(),
                account.getName(),
                account.getSurname(),
                account.getMail(),
                account.getPwd(),
                account.getFollowTags().stream().map(FollowTagModel::of).toList(),
                account.getInscriptions().stream().map(InscriptionModel::of).toList(),
                account.isArchived()
        );
    }
}
