package org.udg.trackdev.spring.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidatorHelper {

    public void validateRequest(BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errors = validation.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ControllerException(String.join(". ", errors));
        }
    }

}
