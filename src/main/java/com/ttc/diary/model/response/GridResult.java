package com.ttc.diary.model.response;

import java.io.Serializable;
import java.util.List;

public class GridResult<T extends Serializable> implements Serializable {
    private long totalItems;
    private long filteredItems;
    private List<T> items;

    public GridResult() {
        //default constructor
    }

    public GridResult(long totalItems, long filteredItems, List<T> items) {
        this.totalItems = totalItems;
        this.filteredItems = filteredItems;
        this.items = items;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public long getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(long filteredItems) {
        this.filteredItems = filteredItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
