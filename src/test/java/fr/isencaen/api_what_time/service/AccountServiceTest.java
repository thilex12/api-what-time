package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.AccountRepository;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.repository.Entity.FollowTag;
import fr.isencaen.api_what_time.repository.Entity.Tag;
import fr.isencaen.api_what_time.repository.FollowRepository;
import fr.isencaen.api_what_time.repository.TagRepository;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.FollowTagModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)

public class AccountServiceTest {
    @InjectMocks
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private TagRepository tagRepository;

    @Test
    public void createAccountTest(){
        CreateAccountModel createAccountModel = new CreateAccountModel(
                "Toto",
                "NOMDEFAMILLE",
                "mail@gmail.com",
                "1234"
        );
        String name, surname, mail, pwd;
        name = accountService.reformatStrEntry(createAccountModel.name());
        surname = accountService.reformatStrEntry(createAccountModel.surname());
        mail = accountService.reformatStrEntry(createAccountModel.mail(), true);
        pwd = createAccountModel.pwd(); // Ne doit pas être formaté...

        Assertions.assertNotNull(name);
        Assertions.assertNotNull(surname);
        Assertions.assertNotNull(mail);
        Assertions.assertNotNull(pwd);

        Assertions.assertFalse(accountService.mailInvalid(mail));
        Mockito.when(accountRepository.findByMail(mail)).thenReturn(List.of());
        Assertions.assertFalse(accountService.mailUsed(mail));

        Assertions.assertEquals(AccountModel.of(new Account(name, surname, mail, pwd)),
                new AccountModel(
                        0,
                        createAccountModel.name(),
                        createAccountModel.surname(),
                        createAccountModel.mail(),
                        createAccountModel.pwd(),
                        List.of(),
                        List.of(),
                        false
                )
        );
    }

    @Test
    public void updateAccount(){
        UpdateAccountModel updateAsked = new UpdateAccountModel(
                "Bruno",
                null,
                "monNouveauMail@gmail.com",
                null,
                List.of(1)
        );

        Account userAccount = new Account(
                1, "Toto", "NOMDEFAMILLE", "mail.toto@gmail.com", "1234"
        );

        // Récupération de la bdd de la ligne de l'utilisateur connecté
        Account bddAccount;
        Mockito.when(accountRepository.findById(userAccount.getId())).thenReturn(
                Optional.of(new Account(userAccount.getId(), userAccount.getName(), userAccount.getSurname(), userAccount.getMail(), userAccount.getPwd()))
        );
        bddAccount = accountRepository.findById(userAccount.getId()).orElseThrow();

        // Récupération des champs
        String name, surname, mail, pwd;
        List<Integer> tags;
        name = accountService.reformatStrEntry(updateAsked.name());
        surname = accountService.reformatStrEntry(updateAsked.surname());
        mail = accountService.reformatStrEntry(updateAsked.mail(), true);
        pwd = updateAsked.pwd(); // Ne pas formatter le mdp
        tags = updateAsked.tags();

        if (mail != null){
            Assertions.assertFalse(accountService.mailInvalid(mail));
            Mockito.when(accountRepository.findByMail(mail)).thenReturn(List.of());
            Assertions.assertFalse(accountService.mailUsed(mail));
        }


        Tag tag = new Tag(1, "NOUVEAU");
        FollowTagModel followTagModel = new FollowTagModel(0, tag.getId(), userAccount.getId(), tag.getName());

        // Si un champ est demandé à être modifié, on le modifie
        if (name != null && !name.isBlank()) bddAccount.setName(name);
        if (surname != null && !surname.isBlank()) bddAccount.setSurname(surname);
        if (mail != null && !mail.isBlank()) bddAccount.setMail(mail);
        if (pwd != null && !pwd.isBlank()) bddAccount.setPwd(pwd);

        Mockito.when(followRepository.findAllByAccount(bddAccount)).thenReturn(List.of());
        Mockito.when(tagRepository.findById(anyInt())).thenReturn(Optional.of(tag));
        Mockito.when(followRepository.save(any())).thenReturn(null);
        if (tags != null){
            accountService.updateTags(bddAccount, tags);
        }

        Assertions.assertEquals(AccountModel.of(bddAccount),
                new AccountModel(
                    bddAccount.getId(),
                    "Bruno",
                    bddAccount.getSurname(),
                    "monnouveaumail@gmail.com",
                    bddAccount.getPwd(),
                    List.of(followTagModel),
                    List.of(),
                    false
                )
        );

    }

    @Test
    public void deleteAccount(){
        Account userAccount = new Account(
                1, "Toto", "NOMDEFAMILLE", "mail.toto@gmail.com", "1234"
        );

        Mockito.when(accountRepository.findById(userAccount.getId())).thenReturn(Optional.of(userAccount));

        Account account = accountRepository.findById(userAccount.getId()).orElseThrow();
        account.setArchived(true);

        Assertions.assertEquals(AccountModel.of(account),
                new AccountModel(
                        account.getId(),
                        account.getName(),
                        account.getSurname(),
                        account.getMail(),
                        account.getPwd(),
                        List.of(),
                        List.of(),
                        true
                ));
    }

    @Test
    public void checkFormatString(){
        String inputString = " BonjoUr";
        Assertions.assertEquals(accountService.reformatStrEntry(inputString), "BonjoUr");
        Assertions.assertEquals(accountService.reformatStrEntry(inputString, true), "bonjour");
    }

    @Test
    public void checkMailValid(){
        String mail = "  toto.gmail.com   ";
        String mailBis = "  toto@gMail.com   ";
        mail = accountService.reformatStrEntry(mail, true);
        mailBis = accountService.reformatStrEntry(mailBis, true);
        Assertions.assertTrue(accountService.mailInvalid(mail));
        Assertions.assertFalse(accountService.mailInvalid(mailBis));
    }

    @Test
    public void checkMailUsed(){
        String mail = "  toto@gmail.com   ";
        mail = accountService.reformatStrEntry(mail);

        Mockito.when(accountRepository.findByMail(mail)).thenReturn(
                List.of(
                    new Account(
                            1,
                            "Toto",
                            "NOMDEFAMILLE",
                            mail,
                            "1234"
                    )
                )
        );
        List<AccountModel> checkMail = accountService.getAccountModelByEmail(mail);

        Assertions.assertFalse(checkMail.isEmpty());
    }


}