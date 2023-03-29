package com.spark.library.util;
import com.spark.library.dao.PersonDao;
import com.spark.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDao personDao;
@Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDao.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Already exists person with same full name.");
        if(person.getBirthDate()==null){
            errors.rejectValue("birthDate", "", "Birthday cannot be blank. Please check.");
        }
    }
}
