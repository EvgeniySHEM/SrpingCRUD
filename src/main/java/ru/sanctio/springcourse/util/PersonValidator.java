package ru.sanctio.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sanctio.springcourse.models.Person;
import ru.sanctio.springcourse.services.PersonService;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        System.out.println(person);

        //посмотреть есть ли человек с таким же email в БД
        if(personService.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is already taken");
        }

        if(person.getDateOfBirth() != null &&
                !person.getDateOfBirth().toString().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            errors.rejectValue("dateOfBirth", "", "This date is not valid");
        }

    }
}
