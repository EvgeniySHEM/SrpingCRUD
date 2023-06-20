package ru.sanctio.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sanctio.springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                        .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (1,?,?,?)", person.getName(), person.getAge(),
                person.getEmail());
    }

    public void update(Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?", person.getName(),
                person.getAge(), person.getEmail(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    /**
     * Тестирование производительности пакетной вставки
     */

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.nanoTime();
        people.forEach(person -> {
            jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)", person.getId(),
                    person.getName(), person.getAge(), person.getEmail());
        });
        long after = System.nanoTime();
        System.out.println("Time Ordinary update: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.nanoTime();

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES (?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, people.get(i).getId());
                        preparedStatement.setString(2, people.get(i).getName());
                        preparedStatement.setInt(3, people.get(i).getAge());
                        preparedStatement.setString(4, people.get(i).getEmail() );
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });

        long after = System.nanoTime();
        System.out.println("Time Butch update: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>(1000);
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name"+i, 30, "name" + i + "@mail.ru"));
        }
        return people;
    }
}
