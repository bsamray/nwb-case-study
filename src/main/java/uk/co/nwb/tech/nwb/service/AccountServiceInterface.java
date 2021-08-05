package uk.co.nwb.tech.nwb.service;

import uk.co.nwb.tech.nwb.model.Account;

public interface AccountServiceInterface {

    void create(Account account);

    Account get(String accountId);

}
