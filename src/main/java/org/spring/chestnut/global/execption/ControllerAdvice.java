package org.spring.chestnut.global.execption;

import org.spring.chestnut.global.dto.ResponseDto;
import org.spring.chestnut.global.execption.custom.NotFoundException;
import org.spring.chestnut.global.execption.custom.WorkerException;
import org.springframework.http.ResponseEntity;
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
}
