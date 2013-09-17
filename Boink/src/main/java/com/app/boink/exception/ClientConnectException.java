package com.app.boink.exception;

/**
 * Created by goof_troop on 9/13/13.
 */
public class ClientConnectException extends Exception {

    private ErrorCode e;

    public ClientConnectException() {
        super();
    }

    public ClientConnectException(String message) {
        super(message);
    }

    public ClientConnectException(ErrorCode e) {
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
