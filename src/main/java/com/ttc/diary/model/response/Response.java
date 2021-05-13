package com.ttc.diary.model.response;

import com.ttc.diary.exception.HttpErrorException;
import com.ttc.diary.util.StatusCodeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public Response() {
        //default constructor
    }

    public static <T> BaseResponse<T> from(HttpStatus status, String msg) {
        return new BaseResponse<>(status.value(), msg);
    }

    public static <T> ResponseEntity<BaseResponse<T>> unauthorized(String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse<>(401, msg));
    }

    public static <T> ResponseEntity<BaseResponse<T>> unauthorized(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new BaseResponse<>(code, msg));
    }

    public static <T> ResponseEntity<BaseResponse<T>> badRequest(String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(400, msg));
    }

    public static <T> ResponseEntity<BaseResponse<T>> badRequest(int code, String msg) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(code, msg));
    }

    public static <T> ResponseEntity<BaseResponse<T>> badRequest(int code, String msg, T data) {
        return ResponseEntity
                .badRequest()
                .body(new BaseResponse<>(code, msg, data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(int code, String msg, T body) {
        return ResponseEntity.ok(new BaseResponse<>(code, msg, body));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(T body) {
        return ResponseEntity.ok(new BaseResponse<>(StatusCodeResponse.SUCCESS, StatusCodeResponse.getMessage(StatusCodeResponse.SUCCESS), body));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok() {
        return ResponseEntity.ok(new BaseResponse<>(StatusCodeResponse.SUCCESS, StatusCodeResponse.getMessage(StatusCodeResponse.SUCCESS), null));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(int code, String message) {
        return ResponseEntity.ok(new BaseResponse<>(code, message));
    }

    public static <T> ResponseEntity<BaseResponse<T>> ok(String message, T body) {
        return ResponseEntity.ok(new BaseResponse<>(StatusCodeResponse.SUCCESS, message, body));
    }

    public static <T> ResponseEntity<BaseResponse<T>> httpError(HttpErrorException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(from(e.getStatus(), e.getMessage()));
    }
}
