package com.artsiomhanchar.peopledbweb.web.controller;

import com.artsiomhanchar.peopledbweb.business.model.Person;
import com.artsiomhanchar.peopledbweb.data.PersonRepository;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {

    private PersonRepository personRepository;

    public PeopleController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @ModelAttribute("people")
    public Iterable<Person> getPeople() {
        return personRepository.findAll();
    }

    @ModelAttribute
    public Person getPerson() {
        return new Person();
    }

    @GetMapping
    public String showPeoplePage() {
        return "people";
    }

    @PostMapping
    public String savePerson(@Valid Person person, Errors errors, @RequestParam MultipartFile photoFileName) {
        log.info(person);
        log.info("FileName " + photoFileName.getOriginalFilename());
        log.info("File Size " + photoFileName.getSize());
        log.info("Errors "+ errors);

        if(!errors.hasErrors()) {
            personRepository.save(person);

            return "redirect:people";
        }

        return "people";
    }

    @PostMapping(params = "delete=true")
    public String deletePeople(@RequestParam Optional<List<Long>> selections) {
        log.info(selections);

        if (selections.isPresent()) {
            personRepository.deleteAllById(selections.get());
        }

        return "redirect:people";
    }

    @PostMapping(params = "edit=true")
    public String editPerson(@RequestParam Optional<List<Long>> selections, Model model) {
        log.info(selections);

        if (selections.isPresent()) {
            Optional<Person> person = personRepository.findById(selections.get().get(0));

            model.addAttribute("person", person);
        }

        return "people";
    }
}
