package uk.co.nwb.tech.nwb.service;

import org.springframework.stereotype.Service;
import uk.co.nwb.tech.nwb.datastore.DataStore;
import uk.co.nwb.tech.nwb.exception.InsufficientFundsException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.model.Transaction;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TransactionService implements TransactionServiceInterface {

    @Resource
    private DataStore dataStore;

    @Override
    public void transferFunds(Transaction transaction) {
        BigDecimal transferAmt = BigDecimal.valueOf(transaction.getAmount());
        String fromAcc = transaction.getFromAccount();
        String toAcc = transaction.getToAccount();

        BigDecimal fromAccountBal = BigDecimal.valueOf(dataStore.getAccount(fromAcc).getAmount());
        BigDecimal toAccountBal = BigDecimal.valueOf(dataStore.getAccount(toAcc).getAmount());

        validateTransfer(fromAccountBal, transferAmt);

        dataStore.upsert(Account.builder().account(fromAcc)
                .amount((fromAccountBal.subtract(transferAmt)).doubleValue()).build());
        dataStore.upsert(Account.builder().account(toAcc)
                .amount((toAccountBal.add(transferAmt)).doubleValue()).build());
    }

    private void validateTransfer(BigDecimal bal, BigDecimal transferAmt) {
        if (bal.compareTo(transferAmt) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }
    }

}
