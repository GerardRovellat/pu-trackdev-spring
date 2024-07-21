package org.udg.trackdev.spring.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * The type Email.
 */
@Entity
@Table(name = "emails")
public class Email extends BaseEntityUUID{

    private String destination;

    private LocalDateTime timestamp;

    /**
     * Instantiates a new Email.
     */
    public Email() {
    }

    /**
     * Instantiates a new Email.
     *
     * @param destination the destination
     * @param timestamp   the timestamp
     */
    public Email(String destination, LocalDateTime timestamp) {
        this.destination = destination;
        this.timestamp = timestamp;
    }

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
