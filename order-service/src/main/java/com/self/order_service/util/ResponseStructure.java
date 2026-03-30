package com.self.order_service.util;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseStructure<T> {
    private int statusCode;
    private String message;
    private T data;
}
