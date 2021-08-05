package uk.co.nwb.tech.nwb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.nwb.tech.nwb.datastore.DataStore;
import uk.co.nwb.tech.nwb.exception.DuplicateAccountException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.util.TestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private DataStore dataStore;

    @Test
    public void testAccountCreateSuccess() {
        Account account = TestUtils.buildAccount("acc1", 123.5);
        when(dataStore.getAccounts()).thenReturn(List.of());

        accountService.create(account);

        verify(dataStore, times(1)).upsert(account);
    }

    @Test
    public void testAccountCreateSuplicateException() {
        Account account = TestUtils.buildAccount("acc1", 123.5);
        when(dataStore.getAccounts()).thenReturn(List.of(account));

        assertThrows(DuplicateAccountException.class, () -> accountService.create(account));
    }

    @Test
    public void testGetAccount() {
        Account account = TestUtils.buildAccount("acc1", 123.5);
        when(dataStore.getAccount("acc1")).thenReturn(account);

        Account result = accountService.get("acc1");

        assertEquals(account, result);
    }

}
