package org.wltech.demo.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    public BusinessException(String message , String code) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,code,message);
    }
}
