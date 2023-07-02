package ru.sanctio.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sanctio.springcourse.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
