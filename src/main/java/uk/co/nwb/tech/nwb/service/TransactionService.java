package uk.co.nwb.tech.nwb.service;

import org.springframework.stereotype.Service;
import uk.co.nwb.tech.nwb.datastore.DataStore;
import uk.co.nwb.tech.nwb.exception.InsufficientFundsException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.model.Transaction;

import javax.annotation.Resource;

@Service
public class TransactionService implements TransactionServiceInterface {

    @Resource
    private DataStore dataStore;

    @Override
    public void transferFunds(Transaction transaction) {
        Double transferAmt = transaction.getAmount();
        String fromAcc = transaction.getFromAccount();
        String toAcc = transaction.getToAccount();

        Double fromAccountBal = dataStore.getAccount(fromAcc).getAmount();
        Double toAccountBal = dataStore.getAccount(toAcc).getAmount();

        validateTransfer(fromAccountBal, transferAmt);

        dataStore.upsert(Account.builder().account(fromAcc).amount(fromAccountBal - transferAmt).build());
        dataStore.upsert(Account.builder().account(toAcc).amount(toAccountBal + transferAmt).build());
    }

    private void validateTransfer(Double bal, Double transferAmt) {
        if (bal < transferAmt) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }
    }

}
