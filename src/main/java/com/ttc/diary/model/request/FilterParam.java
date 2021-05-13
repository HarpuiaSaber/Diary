package com.ttc.diary.model.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FilterParam implements Serializable {
    private String field;
    private Object value;
    private ComparisonOperator operator;

    public FilterParam() {
        //default constructor
    }

    public FilterParam(String field, Object value, ComparisonOperator operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }

    public enum ComparisonOperator {
        EQUAL((byte) 0),
        NOT_EQUAL((byte) 1),
        LESS_THAN((byte) 2),
        LESS_THAN_OR_EQUAL((byte) 3),
        GREATER_THAN((byte) 4),
        GREATER_THAN_OR_EQUAL((byte) 5),
        LIKES((byte) 6), //for strings
        STARTS_WITH((byte) 7), //for strings
        ENDS_WITH((byte) 8), //for strings
        IN((byte) 9), // for list item
        NOT_IN((byte) 10), // for list item
        CONTAINS((byte) 11), // for list item
        NOT_CONTAINS((byte) 12); // for list item

        private final byte value;
        private static Map<Byte, ComparisonOperator> map = new HashMap<>();

        private ComparisonOperator(byte value) {
            this.value = value;
        }

        static {
            for (ComparisonOperator type : ComparisonOperator.values()) {
                map.put(type.value, type);
            }
        }

        public static ComparisonOperator valueOf(byte value) {
            return map.get(value);
        }

        public byte getValue() {
            return value;
        }
    }
}
