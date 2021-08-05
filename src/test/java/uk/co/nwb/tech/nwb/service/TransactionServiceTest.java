package uk.co.nwb.tech.nwb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.nwb.tech.nwb.datastore.DataStore;
import uk.co.nwb.tech.nwb.exception.BankAccountNotFoundException;
import uk.co.nwb.tech.nwb.exception.InsufficientFundsException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.model.Transaction;
import uk.co.nwb.tech.nwb.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private DataStore dataStore;

    @Test
    public void testTransferSuccess() {
        Transaction transaction = TestUtils.buildTransaction("acc1", "acc2", 10.5);
        when(dataStore.getAccount(eq("acc1"))).thenReturn(TestUtils.buildAccount("acc1", 130.1));
        when(dataStore.getAccount(eq("acc2"))).thenReturn(TestUtils.buildAccount("acc2", 200.5));

        transactionService.transferFunds(transaction);

        verify(dataStore, times(1)).getAccount("acc1");
        verify(dataStore, times(1)).getAccount("acc2");
        verify(dataStore, times(2)).upsert(any(Account.class));
        verifyNoMoreInteractions(dataStore);
    }

    @Test
    public void testInsufficientFundsException() {
        Transaction transaction = TestUtils.buildTransaction("acc1", "acc2", 110.5);
        when(dataStore.getAccount(eq("acc1"))).thenReturn(TestUtils.buildAccount("acc1", 100.5));
        when(dataStore.getAccount(eq("acc2"))).thenReturn(TestUtils.buildAccount("acc1", 200.5));

        assertThrows(InsufficientFundsException.class, () -> transactionService.transferFunds(transaction));
    }

    @Test
    public void testBankAccountNotFoundException() {
        Transaction transaction = TestUtils.buildTransaction("acc1", "acc2", 110.5);
        when(dataStore.getAccount(eq("acc1"))).thenThrow(BankAccountNotFoundException.class);

        assertThrows(BankAccountNotFoundException.class, () -> transactionService.transferFunds(transaction));
    }

}