package com.ttc.diary.utils;

import java.util.HashMap;
import java.util.Map;

public class StatusCodeResponse {
    public static final Map<Integer, String> STATUS_CODE_OK = new HashMap<Integer, String>(){
        @Override
        public String put(Integer key, String value) {
            return super.put(10000, "Hello");
        }
    };


}
