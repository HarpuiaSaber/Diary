package com.ttc.diary.extension;

import com.ttc.diary.model.request.FilterParam;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SpecificationEx<T> implements Specification<T> {

    private List<FilterParam> filters;

    public SpecificationEx() {
        //Default constructor
    }

    public SpecificationEx(List<FilterParam> filters) {
        this.filters = filters == null ? new ArrayList<>() : filters;
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (FilterParam filter : this.filters) {
            predicates.add(this.create(root, query, criteriaBuilder, filter));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private <T> Predicate create(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, FilterParam filter) {
        Path path = root.get(filter.getField());
        switch (filter.getOperator()) {
            case EQUAL:
                return criteriaBuilder.equal(path, filter.getValue());
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(path, filter.getValue());
            case LESS_THAN:
                if (path.getJavaType().isAssignableFrom(OffsetDateTime.class)) {
                    return criteriaBuilder.lessThan(path, filter.getValue().toString());
                }
                return criteriaBuilder.lessThan(path, OffsetDateTime.parse(filter.getValue().toString()));
            case LESS_THAN_OR_EQUAL:
                if (path.getJavaType().isAssignableFrom(OffsetDateTime.class)) {
                    return criteriaBuilder.not(criteriaBuilder.greaterThan(path, OffsetDateTime.parse(filter.getValue().toString())));
                }
                return criteriaBuilder.not(criteriaBuilder.greaterThan(path, filter.getValue().toString()));
            case GREATER_THAN:
                if (path.getJavaType().isAssignableFrom(OffsetDateTime.class)) {
                    return criteriaBuilder.greaterThan(path, OffsetDateTime.parse(filter.getValue().toString()));
                }
                return criteriaBuilder.greaterThan(path, filter.getValue().toString());
            case GREATER_THAN_OR_EQUAL:
                if (path.getJavaType().isAssignableFrom(OffsetDateTime.class)) {
                    return criteriaBuilder.not(criteriaBuilder.lessThan(path, OffsetDateTime.parse(filter.getValue().toString())));
                } else return criteriaBuilder.not(criteriaBuilder.lessThan(path, filter.getValue().toString()));
            case LIKES:
                return criteriaBuilder.like(path, "%" + filter.getValue() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(path, filter.getValue() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(path, "%" + filter.getValue());
            case IN:
                return path.in(filter.getValue());
            case NOT_IN:
                return criteriaBuilder.not(path.in(filter.getValue()));
            case CONTAINS:
                return criteriaBuilder.isMember(filter.getValue(), path);
            case NOT_CONTAINS:
                return criteriaBuilder.isNotMember(filter.getValue(), path);
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }
}
