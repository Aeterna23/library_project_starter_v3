package com.spark.library.controller;

import com.spark.library.dao.PersonDao;
import com.spark.library.model.Person;
import com.spark.library.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personDao.findAll());
        return "people/getall";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("person", personDao.findById(id));
        model.addAttribute("books", personDao.getPersonBooksById(id));
        return "people/getperson";
    }

    @GetMapping("/newperson")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/newperson";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/newperson";
        }
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/getupdateperson")
    public String getUpdatePerson( Model model,@PathVariable("id") Integer id) {
        model.addAttribute("person", personDao.findById(id));
        return "people/getupdateperson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult result,@PathVariable("id") Integer id ) {
        personValidator.validate(person,result );
        if (result.hasErrors()) {
            return "people/getupdateperson";
        }
        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Integer id){
        personDao.delete(id);
        return "redirect:/people";
    }


}
