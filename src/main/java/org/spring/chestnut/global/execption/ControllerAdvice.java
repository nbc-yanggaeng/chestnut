package org.spring.chestnut.global.execption;

import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.execption.custom.NotFoundException;
import org.spring.chestnut.global.execption.custom.WorkerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> notFoundException(Exception e) {
        return ResponseDto.badRequest(e.getMessage());
    }

    @ExceptionHandler(WorkerException.class)
    public ResponseEntity<ResponseDto<Void>> workerException(Exception e) {
        return ResponseDto.badRequest(e.getMessage());
    }

    @ExceptionHandler(ColumnNotFoundException.class)
    public ResponseEntity<ResponseDto<Void>> BadRequestExceptionHandler(Exception e) {
        return ResponseDto.badRequest(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Void>> handleIllegalArgumentException(
        IllegalArgumentException e) {
        return ResponseDto.badRequest(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseDto<Void>> handleMissingServletRequestParameterException(
        MissingServletRequestParameterException e) {
        return ResponseDto.badRequest("올바른 파라미터 값이 아닙니다.");
    }
}
