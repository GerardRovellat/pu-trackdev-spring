package org.udg.trackdev.spring.entity.sprintchanges;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.udg.trackdev.spring.service.Global;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * The type Sprint start date change.
 */
@Entity
@DiscriminatorValue(value = SprintStartDateChange.CHANGE_TYPE_NAME)
public class SprintStartDateChange extends SprintChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "start_date_change";

    /**
     * Instantiates a new Sprint start date change.
     */
    public SprintStartDateChange() {}

    /**
     * Instantiates a new Sprint start date change.
     *
     * @param author the author
     * @param sprint the sprint
     * @param value  the value
     */
    public SprintStartDateChange(String author, Long sprint, Date value) {
        super(author, sprint);
        this.startDate = value;
    }

    private Date startDate;

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getStartDate() {
        return this.startDate;
    }
}
