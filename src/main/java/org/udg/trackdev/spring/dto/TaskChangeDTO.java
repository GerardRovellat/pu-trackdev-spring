package org.udg.trackdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskChangeDTO {

    private String id;

    private String changedAt;

    private String author;

    private String type;

    private String oldValue;

    private String newValue;

}
