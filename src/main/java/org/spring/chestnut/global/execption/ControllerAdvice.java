package org.spring.chestnut.global.execption;

import org.spring.chestnut.global.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({
        ColumnNotFoundException.class,
    })
    public ResponseEntity<ResponseDto> BadRequestExceptionHandler(Exception e) {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseDto.badRequest(e.getMessage());
    }
}
