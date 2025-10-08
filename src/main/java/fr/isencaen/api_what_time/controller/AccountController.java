package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.controller.Dto.LoginAccountDto;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("api/v1/accounts/me")
    public AccountDto login(@RequestBody LoginAccountDto loginAccountDto){
        List<AccountModel> listModels = accountService.getAccountByEmail(loginAccountDto.mail());
        AccountModel user = listModels.getFirst();
        return AccountDto.of(user);
    }

    @GetMapping("api/v1/test")
    public List<Integer> testRoad(){
        return List.of();
    }
}
