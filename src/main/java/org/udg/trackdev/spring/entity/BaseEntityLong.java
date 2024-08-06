package org.udg.trackdev.spring.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Base entity long.
 */
@MappedSuperclass
public abstract class BaseEntityLong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() { return id; }
}