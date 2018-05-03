package com.recharge.protal.common;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by geely
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse implements Serializable {


    private int code;
    private Object data;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ServerResponse(int code, Object data, String error) {
        this.code = code;
        this.data = data;
        this.error = error;
    }

    private static int SUCCESS_CODE = 1;
    private static int ERROR_CODE = 0;
    private static int NOT_LOGIN_CODE = -1;

    public static ServerResponse success() {
        return new ServerResponse(SUCCESS_CODE, null, null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(SUCCESS_CODE, data, null);
    }

    public static ServerResponse error() {
        return new ServerResponse(ERROR_CODE, null, null);
    }

    public static ServerResponse error(String message) {
        return new ServerResponse(ERROR_CODE, null, message);
    }

    public static ServerResponse notLogin() {
        return new ServerResponse(NOT_LOGIN_CODE, null, null);
    }

}
