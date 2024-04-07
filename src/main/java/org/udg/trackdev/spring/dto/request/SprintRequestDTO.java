package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.SprintStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintRequestDTO {

    @NotBlank
    @Size(max = 50)
    public String name;

    @NotNull
    public Date startDate;

    @NotNull
    public Date endDate;

    @NotNull
    public SprintStatus status;

}