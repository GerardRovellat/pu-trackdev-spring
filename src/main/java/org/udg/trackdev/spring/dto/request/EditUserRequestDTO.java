package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequestDTO {

    public String username;

    public String color;

    public String capitalLetters;

    public Boolean changePassword;

    public String githubToken;

    public Boolean enabled;

}
