package com.spark.library.services;

import com.spark.library.model.Book;
import com.spark.library.model.Person;
import com.spark.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
       return peopleRepository.findAll();
    }

    public Person findById(int id){
         Optional<Person> person = peopleRepository.findById(id);
         return person.orElse(null);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Person findByFullName(String fullName){
        Optional<Person> person = peopleRepository.findByFullName(fullName);
        return  person.orElse(null);
    }

    public List<Book> findBooks(int id){
        List<Book> books = new ArrayList<>();
        Optional<Person> person =peopleRepository.findById(id);
        person.ifPresent(value -> books.addAll(value.getBooks()));
        System.out.println(books);
      return books;
    }





}
