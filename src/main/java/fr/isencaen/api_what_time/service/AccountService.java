package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<AccountModel> getAccountByEmail(String mail){
        return accountRepository.findByMail(mail).stream().map(AccountModel::of).toList();
    }

    public AccountModel createAccount(CreateAccountModel createAccountModel){
        return AccountModel.of(
                accountRepository.save(new Account(createAccountModel.name(), createAccountModel.surname(), createAccountModel.mail(), createAccountModel.mdp()))
        );
    }
}
