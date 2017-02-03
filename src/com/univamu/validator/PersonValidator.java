package com.univamu.validator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.routines.EmailValidator;

import com.univamu.model.Person;
import com.univamu.service.PersonService;

@Component
public class PersonValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private PersonService personService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	/**
	 * Validate a given person being created
	 */
	@Override
	public void validate(Object o, Errors err) {
		Person person = (Person) o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "firstname", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "lastname", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "password", "NotEmpty");
		
		if(!EmailValidator.getInstance().isValid(person.getEmail()))
			err.rejectValue("email", "NotValid.personForm.email");
		else
			if(personService.findByEmail(person.getEmail()).getId() != 0 && personService.findByEmail(person.getEmail()).getId() != person.getId())
				err.rejectValue("email", "Duplicate.personForm.email");
			else
				ValidationUtils.rejectIfEmptyOrWhitespace(err, "email", "NotEmpty");
		
		/* TODO: IMPLEMENT THAT
		if (!person.getPasswordConfirm().equals(person.getPassword())) {
            err.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
        */
	}

}
