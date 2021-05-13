package com.ttc.diary.model.request;

import java.io.Serializable;
import java.util.List;

public class GridParam implements Serializable {
    private List<SortParam> sorts;
    private List<FilterParam> filters;
    private Integer page;
    private Integer length;

    public GridParam() {
        //default constructor
    }

    public GridParam(List<SortParam> sorts, List<FilterParam> filters, Integer page, Integer length) {
        this.sorts = sorts;
        this.filters = filters;
        this.page = page;
        this.length = length;
    }

    public List<SortParam> getSorts() {
        return sorts;
    }

    public void setSorts(List<SortParam> sorts) {
        this.sorts = sorts;
    }

    public List<FilterParam> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterParam> filters) {
        this.filters = filters;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
