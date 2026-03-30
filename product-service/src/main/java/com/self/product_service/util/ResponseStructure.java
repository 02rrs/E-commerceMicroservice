package com.self.product_service.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class ResponseStructure<T> implements Serializable {

    private int status;
    private String message;
    private T data;

}
