package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private String reformatStrEntry(String s){
        if (s == null) return null;
        return s.toLowerCase().strip();
    }

    private boolean mailInvalid(String mail){
        if (mail == null) return true;
        // Source du regex : https://www.developpez.net/forums/d220837/java/general-java/langage/verifier-validite-d-adresse-email/
        return !mail.matches(".+@.+\\.[a-z]+");
    }

    private boolean mailUsed(String mail){ // mail doit correspondre au format donné par reformatStrEntry
        if (mail == null) return true;
        List<AccountModel> checkMail = getAccountModelByEmail(mail);
        return !checkMail.isEmpty();
    }

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getUserAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user)) return null;
        return user.getAccount();
    }

    public AccountModel getUserModel(){
        return AccountModel.of(getUserAccount());
    }

    public AccountModel getAccountModelById(int id){
        try{
            return accountRepository.findById(id).stream().map(AccountModel::of).toList().getFirst();
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    public List<AccountModel> getAccountModelByEmail(String mail){
        try{
            return accountRepository.findByMail(mail).stream().map(AccountModel::of).toList();
        }
        catch (EntityNotFoundException e){
            return List.of();
        }
    }

    public AccountModel createAccount(CreateAccountModel createAccountModel){
        String name, surname, mail, pwd;
        name = reformatStrEntry(createAccountModel.name());
        surname = reformatStrEntry(createAccountModel.surname());
        mail = reformatStrEntry(createAccountModel.mail());
        pwd = createAccountModel.pwd(); // Ne doit pas être formaté...


        if (mailInvalid(mail)){
            // Cas où mail donné n'est pas un mail valide
            /*throw new HttpStatusCodeException(HttpStatusCode.valueOf(510)){

            };*/
            return null;
        }

        if(mailUsed(mail)){
            // Cas où mail déjà utilisé
            /*throw new HttpStatusCodeException(HttpStatusCode.valueOf(510)){

            };*/
            return null;
        }

        return AccountModel.of(
                accountRepository.save(new Account(name, surname, mail, bCryptPasswordEncoder.encode(pwd)))
        );
    }

    @Transactional
    public AccountModel updateAccount(UpdateAccountModel updateAsked){
        // Récupération de la bdd de la ligne de l'utilisateur connecté
        Account userAccount = getUserAccount();
        if (userAccount == null) return null;

        Account bddAccount = accountRepository.findById(userAccount.getId()).orElseThrow();

        if (bddAccount == null){
            // Cas où le compte demandé n'existe pas (peu probable)
            return null;
        }

        // Récupération des champs
        String name, surname, mail, pwd;
        name = reformatStrEntry(updateAsked.name());
        surname = reformatStrEntry(updateAsked.surname());
        mail = reformatStrEntry(updateAsked.mail());
        pwd = updateAsked.pwd(); // Ne pas formatter le mdp

        if (mail != null){
            if (mailInvalid(mail)){
                // Cas où mail donné n'est pas un mail valide

                return null;
                /*throw new HttpStatusCodeException(HttpStatusCode.valueOf(510)){

                };*/

            }

            if(mailUsed(mail)){
                // Cas où mail déjà utilisé
                /*throw new HttpStatusCodeException(HttpStatusCode.valueOf(520)){

                };*/
                return null;
            }
        }

        // Si un champ est demandé à être modifié, on le modifie
        if (name != null && !name.isBlank()) bddAccount.setName(updateAsked.name());
        if (surname != null && !surname.isBlank()) bddAccount.setSurname(updateAsked.surname());
        if (mail != null && !mail.isBlank()) bddAccount.setMail(updateAsked.mail());
        if (pwd != null && !pwd.isBlank()) bddAccount.setPwd(bCryptPasswordEncoder.encode(updateAsked.pwd()));

        return AccountModel.of(bddAccount);
    }

    @Transactional
    public AccountModel deleteAccount(){
        Account userAccount = getUserAccount();
        if (userAccount == null) return null;

        Account account = accountRepository.findById(userAccount.getId()).orElseThrow();
        if (account == null) return null;

        account.setArchived(true);
        return AccountModel.of(account);
    }

}
