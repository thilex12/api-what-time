package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.controller.Dto.RegisterAccountDto;
import fr.isencaen.api_what_time.controller.Dto.UpdateAccountDto;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("v1/accounts/me")
    public AccountDto update(@RequestBody UpdateAccountDto updateAccountDto){
        AccountModel user = accountService.updateAccount(UpdateAccountModel.of(updateAccountDto));
        return AccountDto.of(user);
    }

    // Uniquement pour débug
    @GetMapping("v1/accounts/test")
    public List<Integer> testRoad(){
        return List.of();
    }
}
