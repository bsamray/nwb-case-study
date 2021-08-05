package uk.co.nwb.tech.nwb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.nwb.tech.nwb.exception.BankAccountNotFoundException;
import uk.co.nwb.tech.nwb.exception.InsufficientFundsException;
import uk.co.nwb.tech.nwb.model.Transaction;
import uk.co.nwb.tech.nwb.service.TransactionServiceInterface;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceInterface transactionService;

    @Test
    public void testTransactionApiSuccess() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getTransactionJson("acc1", "acc2", 100.1)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testTransactionApi404Error() throws Exception {
        doThrow(BankAccountNotFoundException.class).when(transactionService).transferFunds(any(Transaction.class));

        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getTransactionJson("acc1", "acc2", 100.1)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testTransactionApi400Error() throws Exception {
        doThrow(InsufficientFundsException.class).when(transactionService).transferFunds(any(Transaction.class));

        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getTransactionJson("acc1", "acc2", 100.1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTransactionApi400ErrorBadInputFormat() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getTransactionJson("acc1", "acc2", 100.1).substring(3)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTransactionApi400ErrorBadInputType() throws Exception {
        mockMvc.perform(post("/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getTransactionJson("acc1", "acc2", -100.1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private String getTransactionJson(String fromAccount, String toAccount, Double amount) {
        return new StringBuilder().append("{\"from_account\":\"")
                .append(fromAccount)
                .append("\", \"to_account\":\"")
                .append(toAccount)
                .append("\", \"amount\":")
                .append(amount)
                .append("}")
                .toString();
    }

}
