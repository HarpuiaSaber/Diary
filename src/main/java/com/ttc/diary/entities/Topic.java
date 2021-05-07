package com.ttc.diary.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "topic")
public class Topic extends BaseEntity<Long> {
    @Column(unique = true)
    private String name;

    public Topic() {
    }

    public Topic(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
