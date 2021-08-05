package uk.co.nwb.tech.nwb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.nwb.tech.nwb.exception.BankAccountNotFoundException;
import uk.co.nwb.tech.nwb.exception.DuplicateAccountException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.model.Transaction;
import uk.co.nwb.tech.nwb.service.AccountServiceInterface;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceInterface accountService;

    @Test
    public void testAccountCreateApiSuccess() throws Exception {
        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getAccountJson("acc1", 100.1)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAccountCreateApiValidationError() throws Exception {
        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getAccountJson("acc1", -100.1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAccountApi400ErrorBadInputFormat() throws Exception {
        mockMvc.perform(post("/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getAccountJson("acc1", 100.1).substring(3)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAccountApi404Error() throws Exception {
        doThrow(DuplicateAccountException.class).when(accountService).create(any(Account.class));

        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getAccountJson("acc1", 100.1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private String getAccountJson(String account, Double amount) {
        return new StringBuilder().append("{\"account\":\"")
                .append(account)
                .append("\", \"amount\":")
                .append(amount)
                .append("}")
                .toString();
    }

}
