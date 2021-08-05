package uk.co.nwb.tech.nwb.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.service.AccountServiceInterface;
import uk.co.nwb.tech.nwb.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountServiceInterface accountService;

    @Test
    public void testAccountCreate() {
        Account account = TestUtils.buildAccountDto("acc1", 23.0);
        ResponseEntity<Void> result = accountController.createAccount(account);

        verify(accountService, times(1)).create(account);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    public void testAccountGet() {
        Account account = TestUtils.buildAccountDto("acc1", 23.0);
        when(accountService.get("acc1")).thenReturn(account);

        ResponseEntity<Account> result = accountController.getAccount("acc1");

        verify(accountService, times(1)).get("acc1");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(account, result.getBody());
    }

}
