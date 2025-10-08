package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.controller.Dto.UpdateAccountDto;
import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public AccountModel getUserModel(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal)) return null;
        AccountPrincipal user = (AccountPrincipal) auth.getPrincipal();
        return AccountModel.of(user.getAccount());
    }

    public List<AccountModel> getAccountByEmail(String mail){
        return accountRepository.findByMail(mail).stream().map(AccountModel::of).toList();
    }

    public AccountModel createAccount(CreateAccountModel createAccountModel){
        return AccountModel.of(
                accountRepository.save(new Account(createAccountModel.name(), createAccountModel.surname(), createAccountModel.mail(), bCryptPasswordEncoder.encode(createAccountModel.mdp())))
        );
    }

    /*
    public AccountModel modifyAccount(UpdateAccountDto updateAccountDto){
        return AccountModel.of(
                accountRepository.
        )
    }*/
}
