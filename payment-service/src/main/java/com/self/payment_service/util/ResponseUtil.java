package com.self.payment_service.util;

public class ResponseUtil {

    public static <T> ResponseStructure<T> buildResponse(
            int statusCode,
            String message,
            T data) {

        ResponseStructure<T> response = new ResponseStructure<>();

        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setData(data);

        return response;
    }
}
