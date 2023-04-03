package com.spark.library.dao;

import com.spark.library.model.Book;
import com.spark.library.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class PersonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
       return session.createSelectionQuery("select p FROM Person p",
                Person.class).getResultList();

    }


    @Transactional(readOnly = true)
    public Person findById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,
                id);
    }


    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }


    @Transactional
    public void update(Integer id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person oldPerson = session.get(Person.class, id);
        oldPerson.setFullName(updatedPerson.getFullName());
        oldPerson.setBirthDate(updatedPerson.getBirthDate());
        oldPerson.setBooks(updatedPerson.getBooks());
    }


    @Transactional
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    //for PersonValidator fullName validation
    @Transactional
    public Optional<Person> getPersonByFullName(String fullName) {
        Session session = sessionFactory.getCurrentSession();
        List<Person> resultList = session.createSelectionQuery("FROM Person p WHERE p.fullName like 'fullName'", Person.class)
                .getResultList();
        return resultList.stream().findAny();
    }

    @Transactional
    public List<Book> getPersonBooksById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        List<Book> books = new ArrayList<>(person.getBooks());
        return books;
    }
}
