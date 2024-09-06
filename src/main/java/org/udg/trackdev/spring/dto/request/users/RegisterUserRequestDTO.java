package org.udg.trackdev.spring.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Register user request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDTO {

    /**
     * The Username.
     */
    @NotBlank
    @Size(min = Constants.MIN_USERNAME_LENGTH, max = Constants.MAX_USERNAME_LENGTH, message = ErrorConstants.INVALID_USERNAME_SIZE)
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$", message = ErrorConstants.INVALID_USERNAME_FORMAT)
    public String username;

    /**
     * The Email.
     */
    @NotBlank
    @Email(message = ErrorConstants.INVALID_MAIL_FORMAT)
    @Size(max = Constants.MAX_EMAIL_LENGTH, message = ErrorConstants.INVALID_MAIL_SIZE)
    public String email;

}
