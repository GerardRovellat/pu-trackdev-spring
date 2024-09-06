package org.udg.trackdev.spring.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The type Email.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emails")
public class Email extends BaseEntityUUID{

    private String destination;

    private LocalDateTime timestamp;

}
