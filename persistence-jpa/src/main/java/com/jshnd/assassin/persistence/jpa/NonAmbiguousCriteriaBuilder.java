package com.jshnd.assassin.persistence.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class NonAmbiguousCriteriaBuilder {

    private CriteriaBuilder wrapped;

    public NonAmbiguousCriteriaBuilder(CriteriaBuilder wrapped) {
        this.wrapped = wrapped;
    }

    public <T> NonAmbiguousCriteriaQuery<T> createQuery(Class<T> resultClass) {
        return new NonAmbiguousCriteriaQuery(wrapped.createQuery(resultClass));
    }

    public Predicate equal(Path path, Object what) {
       return wrapped.equal(path, what);
    }

    public Predicate and(Predicate left, Predicate right) {
        return wrapped.and(left, right);
    }
}
