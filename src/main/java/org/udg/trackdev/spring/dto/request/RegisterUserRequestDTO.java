package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDTO {

    @NotBlank
    @Size(min = User.MIN_USERNAME_LENGTH, max = User.USERNAME_LENGTH, message = ErrorConstants.INVALID_USERNAME_SIZE)
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$", message = ErrorConstants.INVALID_USERNAME_FORMAT)
    public String username;

    @NotBlank
    @Email(message = ErrorConstants.INVALID_MAIL_FORMAT)
    @Size(min = User.MIN_EMAIL_LENGHT, max = User.EMAIL_LENGTH, message = ErrorConstants.INVALID_MAIL_SIZE)
    public String email;

}
