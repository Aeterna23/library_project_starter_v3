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
public class BookDao implements Dao<Integer, Book> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book findById(Integer bookId) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    @Override
    public void save(Book book) {
        jdbcTemplate.update("INSERT  INTO book(name,author,edition) VALUES (?,?,?)", book.getName(), book.getAuthor(), book.getEdition());
    }

    @Override
    public void update(Integer id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ? , edition = ? WHERE id = ?", book.getName(), book.getAuthor(), book.getEdition(), id);

    }

    @Override
    public void delete(Integer bookId) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", bookId);
    }

    public Optional<Person> findBookOwner(Integer bookId) {
        return jdbcTemplate.query("SELECT * FROM person JOIN book b on person.id = b.person_id WHERE b.id = ?",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void returnBook(Integer id) {
        jdbcTemplate.update("UPDATE book SET person_id = NULL WHERE id = ?", id);
    }

    public void appointBook(Integer id, Person holder) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE id = ?", holder.getId(), id);
    }
}
