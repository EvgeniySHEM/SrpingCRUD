package ru.sanctio.springcourse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.springcourse.models.Person;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersonDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public PersonDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional(readOnly = true)
    public void testNPlus1() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Set<Person> people = new HashSet<>(entityManager.createQuery("FROM Person p LEFT JOIN FETCH p.items", Person.class)
                .getResultList());
        people.forEach(p -> System.out.println("Person " + p.getName() + " has: " + p.getItems()));
    }
}
