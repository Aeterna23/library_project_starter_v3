package com.spark.library.dao;

import com.spark.library.model.Book;
import com.spark.library.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<Book> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createSelectionQuery("FROM Book b",
                Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book findById(Integer bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
        session.persist(book);
        return book;
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void update(Integer id, Book updatedBook) {
        Session session = sessionFactory.getCurrentSession();
        Book oldBook = session.get(Book.class, id);
        oldBook.setName(updatedBook.getName());
        oldBook.setAuthor(updatedBook.getAuthor());
        oldBook.setEdition(updatedBook.getEdition());
        oldBook.setOwner(updatedBook.getOwner());


    }

    @Transactional
    public void delete(Integer bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToRemove = session.get(Book.class, bookId);
        session.remove(bookToRemove);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findBookOwner(Integer bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void returnBook(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }
@Transactional
    public void appointBook(Integer id, Person holder) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(holder);
    }
}
