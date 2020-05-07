package com.termos.validator;

import com.termos.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UserDTO userDTO = (UserDTO) o;
        return  userDTO.password != null && userDTO.password.equals(userDTO.matchingPassword);
    }
}