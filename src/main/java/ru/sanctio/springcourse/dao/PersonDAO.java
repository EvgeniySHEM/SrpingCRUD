package ru.sanctio.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.sanctio.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "evgeniysharychenkov";
    private static final String PASSWORD = "";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "select * from person";
            ResultSet resultSet = statement.executeQuery(SQL);
            
            while (resultSet.next()) {
                 Person person = new Person();
                 person.setId(resultSet.getInt("id"));
                 person.setName(resultSet.getString("name"));
                 person.setAge(resultSet.getInt("age"));
                 person.setEmail(resultSet.getString("email"));

                 people.add(person);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;
        try(PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM person WHERE id = ?")) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO person VALUES (1,?,?,?)")) {
            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Person person) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?")) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, person.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM person WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
