package uk.co.nwb.tech.nwb.service;

import org.springframework.stereotype.Service;
import uk.co.nwb.tech.nwb.datastore.DataStore;
import uk.co.nwb.tech.nwb.exception.DuplicateAccountException;
import uk.co.nwb.tech.nwb.model.Account;

import javax.annotation.Resource;

@Service
public class AccountService implements AccountServiceInterface {

    @Resource
    private DataStore dataStore;

    @Override
    public void create(Account account) {
        if (!isAccountPresent(account)) {
            dataStore.upsert(account);
        } else {
            throw new DuplicateAccountException("Duplicate account creation request");
        }
    }

    @Override
    public Account get(String accountId) {
        return dataStore.getAccount(accountId);
    }

    private boolean isAccountPresent(Account account) {
        return dataStore.getAccounts().stream()
                .anyMatch(account1 -> account1.getAccount().equals(account.getAccount()));
    }

}
