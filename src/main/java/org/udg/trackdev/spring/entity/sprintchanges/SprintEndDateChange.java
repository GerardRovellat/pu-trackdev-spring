package org.udg.trackdev.spring.entity.sprintchanges;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.udg.trackdev.spring.service.Global;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * The type Sprint end date change.
 */
@Entity
@DiscriminatorValue(value = SprintEndDateChange.CHANGE_TYPE_NAME)
public class SprintEndDateChange extends SprintChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "end_date_change";

    /**
     * Instantiates a new Sprint end date change.
     */
    public SprintEndDateChange() {}

    /**
     * Instantiates a new Sprint end date change.
     *
     * @param author the author
     * @param sprint the sprint
     * @param date   the date
     */
    public SprintEndDateChange(String author, Long sprint, Date date) {
        super(author, sprint);
        this.endDate = date;
    }

    private Date endDate;

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getEndDate() {
        return this.endDate;
    }
}
