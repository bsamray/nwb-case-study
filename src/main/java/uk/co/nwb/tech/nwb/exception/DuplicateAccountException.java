package uk.co.nwb.tech.nwb.exception;

public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String msg) {
        super(msg);
    }
}
