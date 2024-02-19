package org.wltech.demo.model.common;

import java.util.Date;

import org.wltech.demo.constant.StatusCode;

public class SuccessResponse<T> {

    private StatusResponse status;

    private T data;

    public SuccessResponse() {
        this.status = new StatusResponse(StatusCode.SUC_CODE_200, StatusCode.SUC_DESC_200, new Date());
    }

    public SuccessResponse(T data) {
        this.status = new StatusResponse(StatusCode.SUC_CODE_200, StatusCode.SUC_DESC_200, new Date());
        this.data = data;
    }

    public SuccessResponse(StatusResponse status, T data) {
        this.status = status;
        this.data = data;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
