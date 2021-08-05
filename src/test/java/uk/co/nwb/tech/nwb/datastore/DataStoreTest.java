package uk.co.nwb.tech.nwb.datastore;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.nwb.tech.nwb.exception.BankAccountNotFoundException;
import uk.co.nwb.tech.nwb.model.Account;
import uk.co.nwb.tech.nwb.util.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataStoreTest {

    @Test
    public void testAccountAdd() {
        DataStore dataStore = new InmemoryDataStore();
        List<Account> present = new ArrayList<>();
        present.add(TestUtils.buildAccount("acc1", 100.0));
        ReflectionTestUtils.setField(dataStore, "accounts", present);
        Account account = TestUtils.buildAccount("acc2", 200.0);

        dataStore.upsert(account);

        assertEquals(2, dataStore.getAccounts().size());
        assertEquals("acc1", dataStore.getAccounts().get(0).getAccount());
        assertEquals("acc2", dataStore.getAccounts().get(1).getAccount());
    }

    @Test
    public void testAccountUpdate() {
        DataStore dataStore = new InmemoryDataStore();
        List<Account> present = new ArrayList<>();
        present.add(TestUtils.buildAccount("acc1", 100.0));
        ReflectionTestUtils.setField(dataStore, "accounts", present);
        Account account = TestUtils.buildAccount("acc1", 200.0);

        dataStore.upsert(account);

        assertEquals(1, dataStore.getAccounts().size());
        assertEquals("acc1", dataStore.getAccounts().get(0).getAccount());
        assertEquals(200, dataStore.getAccounts().get(0).getAmount());
    }

    @Test
    public void testAccountGet() {
        DataStore dataStore = new InmemoryDataStore();

        assertThrows(BankAccountNotFoundException.class, () -> dataStore.getAccount("acc1"));
    }

}
