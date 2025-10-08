package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import jakarta.transaction.Transactional;
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
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user)) return null;
        Account userAccount = user.getAccount();
        return AccountModel.of(userAccount);
    }

    public List<AccountModel> getAccountByEmail(String mail){
        return accountRepository.findByMail(mail).stream().map(AccountModel::of).toList();
    }

    public AccountModel createAccount(CreateAccountModel createAccountModel){
        return AccountModel.of(
                accountRepository.save(new Account(createAccountModel.name(), createAccountModel.surname(), createAccountModel.mail(), bCryptPasswordEncoder.encode(createAccountModel.pwd())))
        );
    }

    @Transactional
    public AccountModel updateAccount(UpdateAccountModel updateAsked){
        // Récupération de la bdd de la ligne de l'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user)) return null;
        Account userAccount = user.getAccount();
        Account bddAccount = accountRepository.findById(userAccount.getId()).orElseThrow();

        // Récupération des champs
        String name, surname, mail, pwd;
        name = updateAsked.name();
        surname = updateAsked.surname();
        mail = updateAsked.mail();
        pwd = updateAsked.pwd();

        // Si un champ est demandé à être modifié, on le modifie
        if (name != null && !name.isBlank()) bddAccount.setName(updateAsked.name());
        if (surname != null && !surname.isBlank()) bddAccount.setSurname(updateAsked.surname());
        if (mail != null && !mail.isBlank()) bddAccount.setMail(updateAsked.mail());
        if (pwd != null && !pwd.isBlank()) bddAccount.setPwd(bCryptPasswordEncoder.encode(updateAsked.pwd()));

        return AccountModel.of(bddAccount);
    }
}
