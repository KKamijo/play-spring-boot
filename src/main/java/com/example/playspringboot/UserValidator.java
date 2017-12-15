package com.example.playspringboot;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors err) {

		User user = (User) obj;

		if (user.getPassword().isEmpty()) {
			err.rejectValue("password", null, "may not be empty");
		} else if (user.getPassword().length() < 2 || user.getPassword().length() > 8) {
			err.rejectValue("password", null, "size must be between 2 and 8");
		}
		if (user.getConfirmPassword().isEmpty()) {
			err.rejectValue("confirmPassword", null, "may not be empty");
		} else if (user.getConfirmPassword().length() < 2 || user.getConfirmPassword().length() > 8) {
			err.rejectValue("confirmPassword", null, "size must be between 2 and 8");
		}
	}
}