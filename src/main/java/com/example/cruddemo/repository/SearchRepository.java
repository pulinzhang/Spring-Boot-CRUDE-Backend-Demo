package com.example.cruddemo.repository;

import com.example.cruddemo.dto.SearchRequestDto;
import com.example.cruddemo.dto.SearchedBy;
import com.example.cruddemo.dto.SortBy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public <T> Page<T> findXByCriteria(Class<T> entityClass, SearchRequestDto request) {
        // If searchedFields is null, set default
        if (request.getSearchedfields() == null) {
            List<SearchedBy> list = new ArrayList<>();
            SearchedBy sb = new SearchedBy();
            sb.setFieldName("id");
            sb.setValues(Arrays.asList("0"));
            list.add(sb);
            request.setSearchedfields(list);
        }

        // If sortByFields is empty, set default sort
        if (request.getSortbyfields() == null || request.getSortbyfields().size() == 0) {
            List<SortBy> defaultSort = new ArrayList<>();
            SortBy sortBy = new SortBy();
            sortBy.setFieldName("id");
            sortBy.setDirection("DESC");
            defaultSort.add(sortBy);
            request.setSortbyfields(defaultSort);
        }

        // Build sort
        List<Sort.Order> orders = new ArrayList<>();
        for (SortBy sortBy : request.getSortbyfields()) {
            Sort.Direction direction = "ASC".equalsIgnoreCase(sortBy.getDirection())
                    ? Sort.Direction.ASC : Sort.Direction.DESC;
            orders.add(new Sort.Order(direction, sortBy.getFieldName()));
        }
        Sort sort = Sort.by(orders);

        Pageable pageable = PageRequest.of(
                request.getPage() != null ? request.getPage() : 0,
                request.getSize() != null ? request.getSize() : 10,
                sort
        );

        // Build specification
        Specification<T> spec = buildSpecification(request.getSearchedfields());

        // Use JPA query to get results
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);

        // Apply where clause if spec is not null
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        // Apply ordering
        if (!orders.isEmpty()) {
            List<jakarta.persistence.criteria.Order> orderList = new ArrayList<>();
            for (Sort.Order order : orders) {
                if (order.isAscending()) {
                    orderList.add(cb.asc(root.get(order.getProperty())));
                } else {
                    orderList.add(cb.desc(root.get(order.getProperty())));
                }
            }
            query.orderBy(orderList);
        }

        // Execute query with pagination
        jakarta.persistence.TypedQuery<T> jpaQuery = entityManager.createQuery(query);
        jpaQuery.setFirstResult((int) pageable.getOffset());
        jpaQuery.setMaxResults(pageable.getPageSize());

        // Get total count
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(cb.count(countRoot));
        if (spec != null) {
            Predicate predicate = spec.toPredicate(countRoot, countQuery, cb);
            if (predicate != null) {
                countQuery.where(predicate);
            }
        }
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new org.springframework.data.domain.PageImpl<>(
                jpaQuery.getResultList(),
                pageable,
                total
        );
    }

    private <T> Specification<T> buildSpecification(List<SearchedBy> searchedFields) {
        if (searchedFields == null || searchedFields.isEmpty()) {
            return null;
        }

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                for (SearchedBy searchedBy : searchedFields) {
                    String fieldName = searchedBy.getFieldName();
                    List<String> values = searchedBy.getValues();
                    String operator = searchedBy.getOperator();

                    if (fieldName == null || values == null || values.isEmpty()) {
                        continue;
                    }

                    // Handle different operators
                    if (operator == null || "EQ".equalsIgnoreCase(operator)) {
                        predicates.add(cb.equal(root.get(fieldName), values.get(0)));
                    } else if ("NE".equalsIgnoreCase(operator)) {
                        predicates.add(cb.notEqual(root.get(fieldName), values.get(0)));
                    } else if ("LIKE".equalsIgnoreCase(operator)) {
                        predicates.add(cb.like(root.get(fieldName), "%" + values.get(0) + "%"));
                    } else if ("IN".equalsIgnoreCase(operator)) {
                        CriteriaBuilder.In<Object> in = cb.in(root.get(fieldName));
                        for (String value : values) {
                            in.value(value);
                        }
                        predicates.add(in);
                    } else if ("BETWEEN".equalsIgnoreCase(operator) && values.size() >= 2) {
                        predicates.add(cb.between(root.get(fieldName), values.get(0), values.get(1)));
                    }
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
