package jpabook.jpashop2.api;

import lombok.Data;

@Data
public class Result<T> {

    public Result(T data) {
        this.data = data;
    }

    public Result(String statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    private T data;
    private String statusCode;
    private String errorMessage;
}
