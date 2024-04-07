package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryPasswordRequestDTO {

    public String code;
    @NotBlank
    @Size(min = Constants.I_PASSWORD_MINIUM_LENGTH, message = ErrorConstants.PASSWORD_MINIUM_LENGTH)
    @Pattern(regexp = Constants.PASSWORD_REGEX,message = ErrorConstants.INVALID_PASSWORD_FORMAT)
    public String newPassword;

}
