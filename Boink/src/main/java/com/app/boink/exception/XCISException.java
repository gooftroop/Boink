package com.app.boink.exception;

/**
 * Created by goof_troop on 9/22/13.
 */
public class XCISException extends Exception {

    private ErrorCode e;

    public XCISException() {
        super();
    }

    public XCISException(String message) {
        super(message);
    }

    public XCISException(ErrorCode e) {
        super(e.getMessage());
        this.e = e;
    }

    public int getCode() {
        return e.getCode();
    }

    @Override
    public String toString() {
        return "com.app.boink.exception.XCISException()";
    }
}
