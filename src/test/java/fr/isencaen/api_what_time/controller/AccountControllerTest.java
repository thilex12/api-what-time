package fr.isencaen.api_what_time.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.repository.Entity.Account;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AccountService accountService;

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    void getMyAccountInfos() throws Exception{
        Account accountUser = new Account("John", "John", "mail@gmail.com", "1234");
        Mockito.when(accountService.getUserModel()).thenReturn(AccountModel.of(accountUser));


        AccountDto accountDtoCheck = AccountDto.of(AccountModel.of(accountUser));

        // J'appelle un mock
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/accounts/me")
                        .contentType("application/json")
        ).andReturn().getResponse();

        // Transforme liste en liste de bonbons
        //List<AccountDto> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        AccountDto result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});
        Assertions.assertEquals(200, response.getStatus());

        // On vérifie que la liste est bien vide
        Assertions.assertEquals(accountDtoCheck, result);
    }


}