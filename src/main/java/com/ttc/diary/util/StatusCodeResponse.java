package com.ttc.diary.util;

import java.util.HashMap;
import java.util.Map;

public final class StatusCodeResponse {

    private StatusCodeResponse() {
    }

    private static final Map<Integer, String> STATUS_CODE = new HashMap<Integer, String>() {{
        put(1000, "Success");
        put(4001, "This account does not exist!");
        put(4002, "Wrong password");
        put(4003, "This diary does not exist!");
        put(4004, "Can not access this resource!");
    }};

    public static String getMessage(Integer statusCode) {
        return STATUS_CODE.get(statusCode);
    }
}
