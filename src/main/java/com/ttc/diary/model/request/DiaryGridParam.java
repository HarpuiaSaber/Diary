package com.ttc.diary.model.request;

import java.util.List;

public class DiaryGridParam extends GridParam {

    private List<Long> topics;

    public DiaryGridParam() {
        //Default constructor
    }

    public DiaryGridParam(List<Long> topics) {
        this.topics = topics;
    }

    public DiaryGridParam(List<SortParam> sorts, List<FilterParam> filters, Integer page, Integer length, List<Long> topics) {
        super(sorts, filters, page, length);
        this.topics = topics;
    }

    public List<Long> getTopics() {
        return topics;
    }

    public void setTopics(List<Long> topics) {
        this.topics = topics;
    }
}
