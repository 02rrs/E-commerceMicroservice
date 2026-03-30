package com.self.user_service.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseStructure<T> {

    private int statusCode;
    private String message;
    private T data;

    public static <T> ResponseStructure<T> build(HttpStatus status, String message, T data) {

        ResponseStructure<T> response = new ResponseStructure<>();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setData(data);

        return response;
    }
}
