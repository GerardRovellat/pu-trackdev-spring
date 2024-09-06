package org.udg.trackdev.spring.dto.request.sprints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.enums.SprintStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * The type Sprint request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintRequestDTO {

    /**
     * The Name.
     */
    @NotBlank
    @Size(max = 50)
    public String name;

    /**
     * The Start date.
     */
    @NotNull
    public Date startDate;

    /**
     * The End date.
     */
    @NotNull
    public Date endDate;

    /**
     * The Status.
     */
    @NotNull
    public SprintStatus status;

}