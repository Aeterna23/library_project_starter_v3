package com.spark.library.services;

import com.spark.library.model.Book;
import com.spark.library.model.Person;
import com.spark.library.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        List<Book> all = booksRepository.findAll();
        return all;
    }

    public Book findById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Optional<Person> findBookOwner(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.map(b -> Optional.ofNullable(b.getOwner())).orElse(null);
    }

    @Transactional
    public void returnBook(int id) {
        Optional<Book> book = booksRepository.findById(id);
        if (book.isPresent()) {
            book.get().setOwner(null);
            booksRepository.save(book.get());
        }


    }

    @Transactional
    public void appointBook(int id, Person holder) {
        booksRepository.findById(id).stream().findAny().ifPresent(b -> b.setOwner(holder));
    }


}
