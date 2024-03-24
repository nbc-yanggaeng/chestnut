package org.spring.chestnut.global.execption.custom;

public class NotMatchException extends RuntimeException {

    public NotMatchException(String message) {
        super(message);
    }
}
