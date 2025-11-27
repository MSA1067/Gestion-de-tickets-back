package com.iush.ca.transversal.infrastructure.repositories;

import com.iush.ca.transversal.domain.models.entity.Selection;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AttributeSpecifications {

    public static <T> Specification<T> equals(Optional<?> paramValue, String... path) {
        return (root, query, builder) -> {
            if (paramValue.isEmpty()) {
                return builder.conjunction();
            }

            query.distinct(true);

            Path<?> attributePath = getPathFromPathArray(root, path);
            return builder.equal(attributePath, paramValue.get());
        };
    }

    public static <T> Specification<T> in(Optional<List<?>> values, String... path) {
        return (root, query, cb) -> {
            if (values.isEmpty() || values.get().isEmpty()) {
                return cb.conjunction();
            }

            query.distinct(true);

            Path<?> attributePath = getPathFromPathArray(root, path);
            return attributePath.in(values.get());
        };
    }

    public static <T> Specification<T> likeIgnoreCase(Optional<String> paramValue, String... path) {
        return (root, query, builder) -> {
            if (paramValue.isEmpty() || paramValue.get().isBlank()) {
                return builder.conjunction();
            }

            query.distinct(true);

            Path<String> attributePath = (Path<String>) getPathFromPathArray(root, path);
            return builder.like(
                    builder.lower(attributePath),
                    "%" + paramValue.get().toLowerCase() + "%"
            );
        };
    }

    /**
     * Like + validación de "active" en el último join.
     */
    public static <T> Specification<T> likeIgnoreCaseWithActive(Optional<String> paramValue,
                                                                String... path) {
        return (root, query, cb) -> {
            if (paramValue.isEmpty() || paramValue.get().isBlank()) {
                return cb.conjunction();
            }

            query.distinct(true);

            From<?, ?> from = Arrays.stream(path, 0, path.length - 1)
                    .reduce((From<?, ?>) root,
                            (From<?, ?> f, String p) -> getOrCreateJoin(f, p),
                            (f1, f2) -> f1);

            Path<String> attributePath = from.get(path[path.length - 1]);
            Predicate likePredicate = cb.like(cb.lower(attributePath), "%" + paramValue.get().toLowerCase() + "%");
            Predicate activePredicate = cb.equal(from.get("active"), Selection.YES.getValue());

            return cb.and(likePredicate, activePredicate);
        };
    }

    private static Path<?> getPathFromPathArray(Root<?> root, String[] path) {
        From<?, ?> from = Arrays.stream(path, 0, path.length - 1)
                .reduce((From<?, ?>) root,
                        (From<?, ?> f, String p) -> getOrCreateJoin(f, p),
                        (f1, f2) -> f1);

        return from.get(path[path.length - 1]);
    }

    private static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute) {
        return from.getJoins().stream()
                .filter(j -> j.getAttribute().getName().equals(attribute))
                .findFirst()
                .orElseGet(() -> from.join(attribute, JoinType.LEFT));
    }
}
