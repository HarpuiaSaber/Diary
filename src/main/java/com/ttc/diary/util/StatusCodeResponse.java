package com.ttc.diary.util;

import java.util.HashMap;
import java.util.Map;

public final class StatusCodeResponse {

    private StatusCodeResponse() {
    }

    public static final int SUCCESS = 2000;
    public static final int ACCOUNT_NOT_EXIST = 4001;
    public static final int WRONG_PASS = 4002;
    public static final int DIARY_NOT_EXIST = 4003;
    public static final int CAN_NOT_ACCESS = 4004;
    public static final int CAN_NOT_PARSE = 4005;
    public static final int UN_SUPPORTED = 4006;

    private static final Map<Integer, String> STATUS_CODE = new HashMap<Integer, String>() {{
        put(SUCCESS, "Success");
        put(ACCOUNT_NOT_EXIST, "This account does not exist!");
        put(WRONG_PASS, "Wrong password");
        put(DIARY_NOT_EXIST, "This diary does not exist!");
        put(CAN_NOT_ACCESS, "Can not access this resource!");
        put(CAN_NOT_PARSE, "Can not parse this request!");
        put(UN_SUPPORTED, "Unsupported!");
    }};

    public static String getMessage(Integer statusCode) {
        return STATUS_CODE.get(statusCode);
    }
}
