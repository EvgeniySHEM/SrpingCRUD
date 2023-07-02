package ru.sanctio.springcourse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.springcourse.models.Person;

import java.util.Optional;

@Component
public class PersonDAO {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public PersonDAO(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        return entityManager.createQuery("FROM Person p WHERE p.email =:email", Person.class)
                .setParameter("email", email).getResultStream().findAny();
    }


//    /**
//     * Тестирование производительности пакетной вставки
//     */
//
//    public void testMultipleUpdate() {
//        List<Person> people = create1000People();
//        long before = System.nanoTime();
//        people.forEach(person -> {
//            jdbcTemplate.update("INSERT INTO person (name, age, email, address) VALUES (?,?,?,?)",
//                    person.getName(), person.getAge(), person.getEmail(), person.getAddress());
//        });
//        long after = System.nanoTime();
//        System.out.println("Time Ordinary update: " + (after - before));
//    }
//
//    public void testBatchUpdate() {
//        List<Person> people = create1000People();
//        long before = System.nanoTime();
//
//        jdbcTemplate.batchUpdate("INSERT INTO person (name, age, email, address) VALUES (?,?,?,?)",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                        preparedStatement.setString(1, people.get(i).getName());
//                        preparedStatement.setInt(2, people.get(i).getAge());
//                        preparedStatement.setString(3, people.get(i).getEmail());
//                        preparedStatement.setString(4, people.get(i).getAddress());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return people.size();
//                    }
//                });
//
//        long after = System.nanoTime();
//        System.out.println("Time Butch update: " + (after - before));
//    }
//
//    private List<Person> create1000People() {
//        List<Person> people = new ArrayList<>(1000);
//        for (int i = 0; i < 1000; i++) {
//            people.add(new Person( "Name"+i, 30, "name" + i + "@mail.ru", "Страна, Город, 187412"));
//        }
//        return people;
//    }
}
