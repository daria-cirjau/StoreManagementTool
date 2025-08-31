package com.store.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
        String message,
        int status,
        Instant timestamp,
        Object data
) {
    public static ApiResponse ofMessage(String message, int status) {
        return new ApiResponse(message, status, Instant.now(), null);
    }
    public static ApiResponse ofData(Object data, int status) {
        return new ApiResponse(null, status, Instant.now(), data);
    }
    public static ApiResponse of(String message, int status, Object data) {
        return new ApiResponse(message, status, Instant.now(), data);
    }
}
