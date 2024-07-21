package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.udg.trackdev.spring.controller.exceptions.EntityNotFound;
import org.udg.trackdev.spring.entity.BaseEntityLong;
import org.udg.trackdev.spring.repository.BaseRepositoryLong;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.util.List;
import java.util.Optional;

/**
 * The type Base service long.
 *
 * @param <T>    the type parameter
 * @param <Repo> the type parameter
 */
public class BaseServiceLong<T extends BaseEntityLong, Repo extends BaseRepositoryLong<T>> implements IBaseService<T> {

    /**
     * The Repo.
     */
    @Autowired
    Repo repo;

    /**
     * Get t.
     *
     * @param id the id
     * @return the t
     */
    public T get(Long id) {
        Optional<T> oc = this.repo.findById(id);
        if (oc.isEmpty())
            throw new EntityNotFound(ErrorConstants.ENTITY_NOT_EXIST);
        return oc.get();
    }


    public List<T> search(final Specification<T> specification) {
        return repo.findAll(specification);
    }

    public List<T> getAll() {
        return repo.findAll();
    }

    /**
     * Repo repo.
     *
     * @return the repo
     */
    protected Repo repo() { return repo; }

}
