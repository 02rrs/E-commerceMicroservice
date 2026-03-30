package com.self.product_service.util;

public class ResponseUtil {

    public static <T> ResponseStructure<T> buildResponse(
            int status,
            String message,
            T data) {

        ResponseStructure<T> response = new ResponseStructure<>();

        response.setStatus(status);
        response.setMessage(message);
        response.setData(data);

        return response;
    }
}
