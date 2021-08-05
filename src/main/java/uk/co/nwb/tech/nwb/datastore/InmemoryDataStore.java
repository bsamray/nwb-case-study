package uk.co.nwb.tech.nwb.datastore;

import org.springframework.stereotype.Service;
import uk.co.nwb.tech.nwb.exception.BankAccountNotFoundException;
import uk.co.nwb.tech.nwb.model.Account;

import java.util.ArrayList;
import java.util.List;

@Service
public class InmemoryDataStore implements DataStore {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public void upsert(Account account) {
        boolean found = false;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccount().equals(account.getAccount())) {
                accounts.set(i, account);
                found = true;
                break;
            }
        }
        if (!found) {
            accounts.add(account);
        }
    }

    @Override
    public Account getAccount(String accountId) {
        return accounts.stream().filter(account -> accountId.equals(account.getAccount())).findFirst()
                .orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
    }

    @Override
    public List<Account> getAccounts() {
        return accounts;
    }

}
