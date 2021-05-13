package com.ttc.diary.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttc.diary.model.request.GridParam;
import com.ttc.diary.model.request.SortParam;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class SearchUtil {

    private SearchUtil() {
    }

    public static String decodeParam(String param) throws UnsupportedEncodingException {
        return URLDecoder.decode(param, StandardCharsets.UTF_8.toString());
    }

    public static GridParam getGridParam(String decodedParam) throws JsonProcessingException, JsonMappingException {
        return new ObjectMapper().readValue(decodedParam, GridParam.class);
    }

    public static Sort getSort(SortParam sortParam) {
        return Sort.by(Sort.Direction.valueOf(sortParam.getDirection().name()), sortParam.getField());
    }

    public static Sort getSort(List<SortParam> sorts) {
        if (sorts != null && sorts.size() > 0) {
            List<Sort.Order> orders = new ArrayList<>();
            for (SortParam sortParam : sorts) {
                orders.add(new Sort.Order(Sort.Direction.valueOf(sortParam.getDirection().name()), sortParam.getField()));
            }
            return Sort.by(orders);
        } else return null;
    }


}
