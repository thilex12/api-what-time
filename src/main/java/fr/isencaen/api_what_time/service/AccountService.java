package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.FollowTag;
import fr.isencaen.api_what_time.repository.Entity.Tag;
import fr.isencaen.api_what_time.repository.FollowRepository;
import fr.isencaen.api_what_time.repository.TagRepository;
import fr.isencaen.api_what_time.service.Model.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private FollowRepository followRepository;

    public String reformatStrEntry(String s, boolean lower){
        if (s == null) return null;
        if (lower) return s.toLowerCase().strip();
        return s.strip();
    }
    public String reformatStrEntry(String s){ // mail doit correspondre au format donné par reformatStrEntry
        return reformatStrEntry(s, false);
    }

    public boolean mailInvalid(String mail){ // mail doit correspondre au format donné par reformatStrEntry
        if (mail == null) return true;
        // Source du regex : https://www.developpez.net/forums/d220837/java/general-java/langage/verifier-validite-d-adresse-email/
        return !mail.matches(".+@.+\\.[a-z]+");
    }

    public boolean mailUsed(String mail){ // mail doit correspondre au format donné par reformatStrEntry
        if (mail == null) return true;
        List<AccountModel> checkMail = getAccountModelByEmail(mail);
        return !checkMail.isEmpty();
    }

    public AccountService(AccountRepository accountRepository, TagRepository tagRepository, FollowRepository followRepository){
        this.accountRepository = accountRepository;
        this.tagRepository = tagRepository;
        this.followRepository = followRepository;
    }

    // Récupère le compte de l'utilisateur connecté
    public Account getUserAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.isAuthenticated() && auth.getPrincipal() instanceof AccountPrincipal user)) return null;
        return user.getAccount();
    }

    // Génère un AccountModel à partir du compte de l'utilisateur connecté
    public AccountModel getUserModel(){
        return AccountModel.of(getUserAccount());
    }

    // Récupère le compte d'un utilisateur donné (via ID)
    public AccountModel getAccountModelById(int id){
        try{
            return accountRepository.findById(id).stream().map(AccountModel::of).toList().getFirst();
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

    // Récupère une liste de compte suivant un mail donné
    // Si tout est ok dans notre contexte, la liste ne contiendra que 0 ou 1 élément
    public List<AccountModel> getAccountModelByEmail(String mail){
        try{
            return accountRepository.findByMail(mail).stream().map(AccountModel::of).toList();
        }
        catch (EntityNotFoundException e){
            return List.of();
        }
    }

    // Créé un compte à partir d'un model donné
    public AccountModel createAccount(CreateAccountModel createAccountModel){
        String name, surname, mail, pwd;
        name = reformatStrEntry(createAccountModel.name());
        surname = reformatStrEntry(createAccountModel.surname());
        mail = reformatStrEntry(createAccountModel.mail(), true);
        pwd = createAccountModel.pwd(); // Ne doit pas être formaté...

        if (name == null || surname == null || mail == null || pwd == null){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Paramètre manquant");
        }
        if (mailInvalid(mail)){
            // Cas où mail donné n'est pas un mail valide
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid email");
        }

        if(mailUsed(mail)){
            // Cas où mail déjà utilisé
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email already used");
        }

        return AccountModel.of(
                accountRepository.save(new Account(name, surname, mail, bCryptPasswordEncoder.encode(pwd)))
        );
    }

    // Mets à jour le suivi des tags d'un compte donné (issue du repository) à partir d'une liste d'id de tags
    @Transactional
    public void updateTags(Account bddAccount, List<Integer> tags){
        // On liste tous les suivis de tag de l'utilisateur
        List<FollowTag> followTagsInBDD = followRepository.findAllByAccount(bddAccount);

        // On en fait une liste de tags
        List<Tag> followTags = new ArrayList<>();
        for(FollowTag ft : followTagsInBDD){
            followTags.add(ft.getTag());
        }

        // Pour chacun des tags passés en paramètre de la requête
        for(int id : tags){
            try { // Si l'id courant existe :
                // On récupère le tag réel
                Tag tag = tagRepository.findById(id).orElseThrow();

                // On regarde si l'utilisateur suit déjà ce tag
                if (!followTags.contains(tag)){ // Si non, maintenant il le suit
                    followRepository.save(new FollowTag(tag, bddAccount));
                }

                // On supprime le tag de la liste de ceux identifiés précedement
                followTags.remove(tag);

            }
            catch (NoSuchElementException e){}
        }

        // On itère sur chacun des tags identifiés comme suivis, qui n'ont pas été trouvés dans la liste en paramètre
        for (Tag t : followTags){
            // Pour chacun, on identifie l'enregistrement du follow correspondant
            for(FollowTag ft : followTagsInBDD){
                if (t == ft.getTag()){
                    // On supprime la ligne en mettant à jour account (dans removeFollow)
                    ft.removeFollow();
                    followRepository.delete(ft);
                }
            }
        }
    }

    // Mets à jour les informations du compte de l'utilisateur connecté, à partir d'un model groupant les changements
    @Transactional
    public AccountModel updateAccount(UpdateAccountModel updateAsked){
        // Récupération de la bdd de la ligne de l'utilisateur connecté
        Account userAccount = getUserAccount();
        if (userAccount == null) return null;
        Account bddAccount;
        try{
            // Cas où le compte demandé n'existe pas (peu probable)
            bddAccount = accountRepository.findById(userAccount.getId()).orElseThrow();
        }
        catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't match");
        }

        // Récupération des champs
        String name, surname, mail, pwd;
        List<Integer> tags;
        name = reformatStrEntry(updateAsked.name());
        surname = reformatStrEntry(updateAsked.surname());
        mail = reformatStrEntry(updateAsked.mail(), true);
        pwd = updateAsked.pwd(); // Ne pas formatter le mdp
        tags = updateAsked.tags();

        if (mail != null){
            if (mailInvalid(mail)){
                // Cas où mail donné n'est pas un mail valide
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid email");
            }

            if(mailUsed(mail)){
                // Cas où mail déjà utilisé
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email already used");
            }
        }

        // Si un champ est demandé à être modifié, on le modifie
        if (name != null && !name.isBlank()) bddAccount.setName(name);
        if (surname != null && !surname.isBlank()) bddAccount.setSurname(surname);
        if (mail != null && !mail.isBlank()) bddAccount.setMail(mail);
        if (pwd != null && !pwd.isBlank()) bddAccount.setPwd(bCryptPasswordEncoder.encode(pwd));
        if (tags != null) updateTags(bddAccount, tags);
        return AccountModel.of(bddAccount);
    }

    @Transactional
    public AccountModel deleteAccount(){
        Account userAccount = getUserAccount();
        if (userAccount == null) return null;
        try{
            Account account = accountRepository.findById(userAccount.getId()).orElseThrow();
            account.setArchived(true);
            return AccountModel.of(account);
        }
        catch (EntityNotFoundException e){
            return null;
        }
    }

}
