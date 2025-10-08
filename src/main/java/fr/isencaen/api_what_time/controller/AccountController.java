package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.controller.Dto.LoginAccountDto;
import fr.isencaen.api_what_time.controller.Dto.RegisterAccountDto;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.AccountPrincipal;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("v1/accounts/me")
    public AccountDto login(){
        AccountModel userModel = accountService.getUserModel();
        return AccountDto.of(userModel);
    }

    @PostMapping("v1/accounts")
    public AccountDto register(@RequestBody RegisterAccountDto registerAccountDto){
        AccountModel user = accountService.createAccount(CreateAccountModel.of(registerAccountDto));
        return AccountDto.of(user);
    }

    // Uniquement pour débug
    @GetMapping("v1/accounts/test")
    public List<Integer> testRoad(){
        return List.of();
    }
}
