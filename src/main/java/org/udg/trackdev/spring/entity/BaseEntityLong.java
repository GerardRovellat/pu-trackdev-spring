package org.udg.trackdev.spring.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Base entity long.
 */
@MappedSuperclass
@Data
public abstract class BaseEntityLong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}