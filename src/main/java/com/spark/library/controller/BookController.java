package com.spark.library.controller;

import com.spark.library.dao.BookDao;
import com.spark.library.dao.PersonDao;
import com.spark.library.model.Book;
import com.spark.library.model.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;

    private final PersonDao personDao;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("books", bookDao.findAll());

        return "books/getall";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Integer id, Model model,@ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.findById(id));
        Optional<Person> bookOwner = bookDao.findBookOwner(id);
        if (bookOwner.isPresent()){
            model.addAttribute("bookOwner",bookOwner.get());
        } else{
            model.addAttribute("people",personDao.findAll());
        }
        return "books/getbook";
    }

    @GetMapping("/newbook")
    public String getCreateNewBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/newbook";
    }

    @PostMapping()
    public String newBook(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/newbook";
        }
        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/updatebook")
    public String getUpdateBook(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("book", bookDao.findById(id));
        return "books/getupdatebook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult result, @PathVariable("id") Integer id) {
        if (result.hasErrors()) {
            return "books/getupdatebook";
        }
        bookDao.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Integer id){
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping ("/{id}/return")
    public String returnBook(@PathVariable("id") Integer id){
        bookDao.returnBook(id);
        return "redirect:/books/"+id;
    }
    @PatchMapping ("/{id}/appoint")
    public String appointBook(@PathVariable("id") Integer id, @ModelAttribute("person") Person holder){
        bookDao.appointBook(id,holder);
        return "redirect:/books/"+id;
    }
}