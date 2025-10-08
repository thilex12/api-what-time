package fr.isencaen.api_what_time.service.Model;

import fr.isencaen.api_what_time.repository.Entity.Account;

public record AccountModel (
        int id,
        String name,
        String surname,
        String mail,
        String mdp
){
    public static AccountModel of(Account account){
        return new AccountModel(
                account.getId(), account.getName(), account.getSurname(), account.getMail(), account.getMdp()
        );
    }
}
