package org.udg.trackdev.spring.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Change password request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    /**
     * The Old password.
     */
    @NotNull
    public String oldPassword;

    /**
     * The New password.
     */
    @NotBlank
    @Size(min = Constants.I_PASSWORD_MINIUM_LENGTH, message = ErrorConstants.PASSWORD_MINIUM_LENGTH)
    @Pattern(regexp = Constants.PASSWORD_REGEX, message = ErrorConstants.INVALID_PASSWORD_FORMAT)
    public String newPassword;

}
