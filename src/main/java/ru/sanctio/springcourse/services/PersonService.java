package ru.sanctio.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.springcourse.models.Mood;
import ru.sanctio.springcourse.models.Person;
import ru.sanctio.springcourse.repositories.PersonRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findOne(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());

        person.setMood(getMood());
        personRepository.save(person);
    }

    private Mood getMood() {
        final int random = ThreadLocalRandom.current()
                .nextInt(0, Mood.values().length);
        return Arrays.stream(Mood.values())
                .filter(m -> m.ordinal() == random)
                .findAny()
                .get();
    }

    @Transactional
    public void update(Person person) {
        personRepository.save(person); // тут он методом save выполнит обновление, если у него есть id
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public void test() {
        System.out.println("Testing here with debug. Inside Hibernate Transaction");
    }
}
