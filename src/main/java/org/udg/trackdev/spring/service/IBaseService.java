package org.udg.trackdev.spring.service;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * The interface Base service.
 *
 * @param <T> the type parameter
 */
public interface IBaseService<T> {
    /**
     * Search list.
     *
     * @param specification the specification
     * @return the list
     */
    List<T> search(Specification<T> specification);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<T> getAll();
}
