package uk.co.nwb.tech.nwb.util;

import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.model.Transaction;

public class TestUtils {

    public static Account buildAccount(String accountNum, Double balance) {
        return Account.builder()
                .account(accountNum)
                .amount(balance)
                .build();
    }

    public static Transaction buildTransaction(String fromAccount, String toAccount, Double amount) {
        return Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .build();
    }

    public static Account buildAccountDto(String accountNum, Double balance) {
        return Account.builder()
                .account(accountNum)
                .amount(balance)
                .build();
    }

    public static Transaction buildTransactionDto(String fromAccount, String toAccount, Double amount) {
        return Transaction.builder()
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .amount(amount)
                .build();
    }

}
