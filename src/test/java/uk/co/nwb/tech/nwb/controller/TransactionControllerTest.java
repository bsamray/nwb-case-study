package uk.co.nwb.tech.nwb.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.nwb.tech.nwb.model.Transaction;
import uk.co.nwb.tech.nwb.service.TransactionServiceInterface;
import uk.co.nwb.tech.nwb.util.TestUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionServiceInterface transactionService;

    @Test
    public void testAccountCreate() {
        Transaction transaction = TestUtils.buildTransactionDto("acc1", "acc2", 33.5);
        transactionController.transferFunds(transaction);

        verify(transactionService, times(1)).transferFunds(transaction);
    }

}
