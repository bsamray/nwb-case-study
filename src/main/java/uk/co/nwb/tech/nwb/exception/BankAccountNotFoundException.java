package uk.co.nwb.tech.nwb.exception;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException(String msg) {
        super(msg);
    }

}
