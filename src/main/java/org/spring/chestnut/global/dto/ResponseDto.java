package org.spring.chestnut.global.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ResponseDto<T> {

    private String message;
    private T data;

    public static <T> ResponseEntity<ResponseDto<T>> ok(
        String message,
        T data
    ) {
        return ResponseEntity.ok(new ResponseDto<>(message, data));
    }

    public static <T> ResponseEntity<ResponseDto<T>> badRequest(String message) {
        return ResponseEntity.badRequest().body((new ResponseDto<>(message,null)));
    }

    public static <T> ResponseEntity<ResponseDto<T>> of(
        HttpStatus status, String message, T data
    ) {
        return ResponseEntity.status(status).body(new ResponseDto<>(message, data));
    }
}
