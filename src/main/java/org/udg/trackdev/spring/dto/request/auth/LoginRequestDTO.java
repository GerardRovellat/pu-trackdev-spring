package org.udg.trackdev.spring.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * The type Login request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    /**
     * The Email.
     */
    @NotNull
    public String email;

    /**
     * The Password.
     */
    @NotNull
    public String password;

}
