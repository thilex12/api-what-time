package fr.isencaen.api_what_time.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.api_what_time.config.SpringSecurityConfig;
import fr.isencaen.api_what_time.controller.Dto.*;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.FollowTagModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AccountController.class)
@Import(SpringSecurityConfig.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AccountService accountService;

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void getMyAccountInfos() throws Exception{
        // Notre Account de référence
        Account accountUser = new Account("John", "John", "mail@gmail.com", "1234");

        // Modifie le retour du service utilisée par la requête que l'on va appeler
        Mockito.when(accountService.getUserModel()).thenReturn(AccountModel.of(accountUser));

        // Fais la requête
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/accounts/me")
                        .contentType("application/json")
        ).andReturn().getResponse();

        // Récupération du résultat
        AccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        // On vérifie code de retour
        Assertions.assertEquals(200, response.getStatus());

        // On vérifie l'élément
        Assertions.assertEquals(new AccountDto(accountUser.getId(), accountUser.getName(), accountUser.getSurname(), accountUser.getMail(), List.of(), List.of()), result);
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void getAnyAccountInfos() throws Exception {
        // Notre Account de référence
        Account accountUser = new Account(1, "John", "John", "mail@gmail.com", "1234");

        // Modifie le retour du service utilisée par la requête que l'on va appeler
        Mockito.when(accountService.getAccountModelById(1)).thenReturn(AccountModel.of(accountUser));

        // Fais la requête
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/accounts/1")
                        .contentType("application/json")
        ).andReturn().getResponse();

        // Récupération du résultat
        OtherAccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
        });

        // On vérifie code de retour
        Assertions.assertEquals(200, response.getStatus());

        // On vérifie l'élément
        Assertions.assertEquals(new OtherAccountDto(accountUser.getId(), accountUser.getName(), accountUser.getSurname(), accountUser.getMail()), result);
    }

    @Test
    void createAccount() throws Exception{
        RegisterAccountDto toto = new RegisterAccountDto(
                "Toto",
                "NOMDEFAMILLE",
                "toto.famille@gmail.com",
                "12345"
        );
        // Modifie le retour du service utilisée par la requête que l'on va appeler
        Mockito.when(accountService.createAccount(CreateAccountModel.of(toto))).thenReturn(new AccountModel(
                1,
                toto.name(),
                toto.surname(),
                toto.mail(),
                toto.pwd(),
                List.of(),
                List.of(),
                false
        ));
        String body = """
        {
            "name" : "Toto",
            "surname" : "NOMDEFAMILLE",
            "mail" : "toto.famille@gmail.com",
            "pwd" : "12345"
        }
        """;
        // Fais la requête
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/v1/accounts")
                        .with(csrf())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn().getResponse();

        // Récupération du résultat
        AccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        // On vérifie code de retour
        Assertions.assertEquals(201, response.getStatus());

        // On vérifie que l'élément'
        Assertions.assertEquals(new AccountDto(1, toto.name(), toto.surname(), toto.mail(), List.of(), List.of()), result);
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void modifyMyAccount() throws Exception{
        UpdateAccountDto toto = new UpdateAccountDto(
                "Bruno",
                null,
                null,
                null,
                List.of(1)
        );
        FollowTagDto ft = new FollowTagDto(1, 1, 1, "Beebo");


        // Modifie le retour du service utilisée par la requête que l'on va appeler
        Mockito.when(accountService.updateAccount(UpdateAccountModel.of(toto))).thenReturn(new AccountModel(
                1,
                "Bruno",
                toto.surname(),
                toto.mail(),
                toto.pwd(),
                List.of(new FollowTagModel(ft.id(), ft.tagId(), ft.accountId(), ft.nameTag())),
                List.of(),
                false
        ));
        String body = """
        {
            "name" : "Bruno",
            "tags" : [1]
        }
        """;
        // Fais la requête
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.put("/v1/accounts/me")
                        .with(csrf())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn().getResponse();

        // Récupération du résultat
        AccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        // On vérifie code de retour
        Assertions.assertEquals(201, response.getStatus());

        // On vérifie que l'élément'
        Assertions.assertEquals(new AccountDto(1, "Bruno", toto.surname(), toto.mail(), List.of(ft), List.of()), result);
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void deleteMyAccount() throws Exception{
        Account toto = new Account(
                "Toto",
                "NOMDEFAMILLE",
                "toto.famille@gmail.com",
                "12345"
        );

        // Modifie le retour du service utilisée par la requête que l'on va appeler
        Mockito.when(accountService.deleteAccount()).thenReturn(new AccountModel(
                toto.getId(),
                toto.getName(),
                toto.getSurname(),
                toto.getMail(),
                toto.getPwd(),
                List.of(),
                List.of(),
                false
        ));

        // Fais la requête
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/accounts/me")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andReturn().getResponse();

        // Récupération du résultat
        AccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        // On vérifie code de retour
        Assertions.assertEquals(201, response.getStatus());

        // On vérifie l'élément
        Assertions.assertEquals(new AccountDto(toto.getId(), toto.getName(), toto.getSurname(), toto.getMail(), List.of(), List.of()), result);
    }

}