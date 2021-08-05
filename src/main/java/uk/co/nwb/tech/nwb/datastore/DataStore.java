package uk.co.nwb.tech.nwb.datastore;

import uk.co.nwb.tech.nwb.model.Account;

import java.util.List;

public interface DataStore {

    void upsert(Account account);

    Account getAccount(String accountId);

    List<Account> getAccounts();

}
