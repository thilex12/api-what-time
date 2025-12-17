package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.controller.Dto.OtherAccountDto;
import fr.isencaen.api_what_time.controller.Dto.RegisterAccountDto;
import fr.isencaen.api_what_time.controller.Dto.UpdateAccountDto;
import fr.isencaen.api_what_time.service.AccountService;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.CreateAccountModel;
import fr.isencaen.api_what_time.service.Model.UpdateAccountModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("v1/admin-accounts")
    public List<OtherAccountDto> getAllAccounts() {
        List<AccountModel> accounts = accountService.getAllAccounts();
        return accounts.stream().map(OtherAccountDto::of).toList();
    }

    @GetMapping("v1/admin-accounts/{accountId}")
    public AccountDto getAccountById(
            @PathVariable Integer accountId
    ) {
        AccountModel account = accountService.getAccountModelById(accountId);
        if (account == null) return null;
        return AccountDto.of(account);
    }

    @GetMapping("v1/accounts/me")
    public AccountDto login() {
        AccountModel userModel = accountService.getUserModel();
        if (userModel == null) return null;
        return AccountDto.of(userModel);
    }

    @PostMapping("v1/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto register(@RequestBody RegisterAccountDto registerAccountDto) {
        AccountModel user = accountService.createAccount(CreateAccountModel.of(registerAccountDto));
        if (user == null) return null;
        return AccountDto.of(user);
    }

    @PutMapping("v1/accounts/me")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto update(@RequestBody UpdateAccountDto updateAccountDto) {
        AccountModel user = accountService.updateAccount(UpdateAccountModel.of(updateAccountDto));
        if (user == null) return null;
        return AccountDto.of(user);
    }

    @DeleteMapping("v1/accounts/me")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto deleteMe() {
        AccountModel userDeleted = accountService.deleteAccount();
        if (userDeleted == null) return null;
        return AccountDto.of(userDeleted);
    }

    @GetMapping("v1/accounts/{accountId}")
    public OtherAccountDto getOtherAccountInfo(@PathVariable Integer accountId) {
        AccountModel account = accountService.getAccountModelById(accountId);
        if (account == null) return null;
        return OtherAccountDto.of(account);
    }

}
