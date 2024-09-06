package org.udg.trackdev.spring.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Base entity uuid.
 */
@MappedSuperclass
@Data
public abstract class BaseEntityUUID {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = Constants.UUID_LENGTH)
    private String id;

}