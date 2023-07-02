package ru.sanctio.springcourse.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sanctio.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<Person> index() {
//        try(Session session = sessionFactory.getCurrentSession()) {
//            return session.createSelectionQuery("FROM Person", Person.class).getResultList();
//        }
        Session session = sessionFactory.getCurrentSession();
        return session.createSelectionQuery("FROM Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(String email) {
        Session session = sessionFactory.getCurrentSession();

        return session.createSelectionQuery("FROM Person p WHERE p.email =:email", Person.class)
                .setParameter("email", email).stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public void update(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(person);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.find(Person.class,id);
        session.remove(person);
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
