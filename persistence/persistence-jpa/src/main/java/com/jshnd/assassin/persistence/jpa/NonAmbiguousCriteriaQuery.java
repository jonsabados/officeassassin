package com.jshnd.assassin.persistence.jpa;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Scala hates all the method overloading criteria builders do, so here's a wrapper for the stuff we need.
 */
public class NonAmbiguousCriteriaQuery<T> {

    private CriteriaQuery<T> wrapped;

    public NonAmbiguousCriteriaQuery(CriteriaQuery<T> wrapped) {
        this.wrapped = wrapped;
    }

    public <V> Root<V> from(Class<V> clazz) {
        return wrapped.from(clazz);
    }

    public NonAmbiguousCriteriaQuery<T> where(Predicate predicate) {
        return new NonAmbiguousCriteriaQuery(wrapped.where(predicate));
    }

    public CriteriaQuery<T> unwrap() {
        return wrapped;
    }

}
