package com.chs.property_service.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class APIResponse extends BasicResponse{

    private Object content;

    public void  setSuccess(Object body) {
        this.setSuccess(true);
        this.setMessage("");
        this.content = body;
    }

    public void setFail(String message) {
        this.setSuccess(false);
        this.setMessage(message);
        this.content = null;
    }
}