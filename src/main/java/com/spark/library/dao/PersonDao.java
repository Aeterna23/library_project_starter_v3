package com.spark.library.dao;

import com.spark.library.model.Book;
import com.spark.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class PersonDao implements Dao<Integer, Person> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person findById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fullname, birthdate) VALUES (?,?)",
                person.getFullName(), person.getBirthDate());
    }

    @Override
    public void update(Integer id, Person person) {
        jdbcTemplate.update("UPDATE person SET fullname = ?, birthdate = ? WHERE id =?",
                person.getFullName(), person.getBirthDate(), id);

    }

    @Override
    public void delete(Integer id) {
         jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }
//for PersonValidator fullName validation

    public Optional<Person> getPersonByFullName(String fullName) {
        return  jdbcTemplate.query("SELECT * FROM person WHERE fullname = ?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public List<Book> getPersonBooksById(Integer id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
