package com.app.boink.exception;

/**
 * Created by goof_troop on 9/12/13.
 */
public class PickleException extends Exception {

    private ErrorCode e;

    public PickleException() {
        super();
    }

    public PickleException(String message) {
        super(message);
    }

    public PickleException(ErrorCode e) {
        super(e.getMessage());
        this.e = e;
    }

    public int getCode() {
        return e.getCode();
    }

    @Override
    public String toString() {
        return "com.app.boink.exception.PickleException()";
    }
}
