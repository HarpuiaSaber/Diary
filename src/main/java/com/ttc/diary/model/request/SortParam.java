package com.ttc.diary.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SortParam implements Serializable {
    @NotBlank(message = "field is mandatory")
    private String field;
    @NotNull(message = "direction may not be null")
    private SortDirection direction;

    public SortParam() {
        //default constructor
    }

    public SortParam(String field, SortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }

    public enum SortDirection {
        ASC((byte) 0), DESC((byte) 1);

        private final byte value;
        private static Map<Byte, SortDirection> map = new HashMap<>();

        private SortDirection(byte value) {
            this.value = value;
        }

        static {
            for (SortDirection type : SortDirection.values()) {
                map.put(type.value, type);
            }
        }

        public static SortDirection valueOf(byte value) {
            return map.get(value);
        }

        public byte getValue() {
            return value;
        }
    }
}
