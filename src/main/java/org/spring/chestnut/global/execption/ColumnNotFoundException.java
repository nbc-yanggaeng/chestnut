package org.spring.chestnut.global.execption;

public class ColumnNotFoundException extends RuntimeException {

    public ColumnNotFoundException(String message) {
        super(message);
    }
}
