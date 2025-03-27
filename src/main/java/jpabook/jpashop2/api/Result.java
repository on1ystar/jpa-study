package jpabook.jpashop2.api;

import lombok.Data;

@Data
public class Result<T> {

    public Result(T data) {
        this.data = data;
    }

    private T data;
}
