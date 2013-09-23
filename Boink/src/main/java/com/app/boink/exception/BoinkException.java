package com.app.boink.exception;

/**
 * Created by goof_troop on 9/22/13.
 */
public class BoinkException extends Exception {

    private ErrorCode e;

    public BoinkException() {
        super();
    }

    public BoinkException(String message) {
        super(message);
    }

    public BoinkException(ErrorCode e) {
        super(e.getMessage());
        this.e = e;
    }

    public int getCode() {
        return e.getCode();
    }

    @Override
    public String toString() {
        return "com.app.boink.exception.BoinkException()";
    }
}
