package uk.co.nwb.tech.nwb.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.service.AccountServiceInterface;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Resource
    private AccountServiceInterface accountService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAccount(@Valid @RequestBody Account account) {
        accountService.create(account);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@NotEmpty @NotNull @PathVariable String accountId) {
        Account account = accountService.get(accountId);
        return ResponseEntity.ok(account);
    }

}
