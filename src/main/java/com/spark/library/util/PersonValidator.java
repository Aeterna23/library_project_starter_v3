package com.spark.library.util;

import com.spark.library.model.Person;
import com.spark.library.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (peopleService.findByFullName(person.getFullName())!=null)
            errors.rejectValue("fullName", "", "Already exists person with same full name.");
        if (person.getBirthDate() == null) {
            errors.rejectValue("birthDate", "", "Birthday cannot be blank. Please check.");
        }
    }
}
