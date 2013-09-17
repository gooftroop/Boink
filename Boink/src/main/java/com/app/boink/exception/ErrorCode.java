package com.app.boink.exception;

/**
 * Created by goof_troop on 9/12/13.
 */
public enum ErrorCode {

    INVALID_USERNAME(0, "The user name provided is invalid."),
    INVALID_PASSWORD(1, "The password provided is invalid."),
    FILE_NOT_FOUND(2, "File does not exist."),
    OBJECT_STREAM_ERROR(3, "Unable to create ObjectOutputStream."),
    OBJECT_WRITE_ERROR(4, "Unable to write object."),
    OBJECT_READ_ERROR(5, "Failed to read object from file."),
    CLASS_CAST_ERROR(6, "Failed to cast object to class."),
    CLASS_NOT_FOUND_ERROR(7, "Casting class not found."),
    PICKLE_REFERENCE_ERROR(8, "Common Name provided to pickle does not exist."),
    CONNECTION_CLIENT_INIT_ERROR(9, "The client connection has not been initialized.");

    private final int code;
    private final String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return code + ": " + message;
    }
}