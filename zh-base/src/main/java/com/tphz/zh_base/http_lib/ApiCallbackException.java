package com.tphz.zh_base.http_lib;

public class ApiCallbackException extends Exception{
    public ApiCallbackException(Throwable cause) {
        super(cause);
    }

    public ApiCallbackException(String message) {
        super(message);
    }
}
