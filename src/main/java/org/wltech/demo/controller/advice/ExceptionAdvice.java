package org.wltech.demo.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.wltech.demo.exception.BaseException;
import org.wltech.demo.exception.BusinessException;
import org.wltech.demo.model.common.ErrorResponse;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse<Object>(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(ex.getStatus()).body(
                new ErrorResponse<Object>(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse<Object>());
    }
}
