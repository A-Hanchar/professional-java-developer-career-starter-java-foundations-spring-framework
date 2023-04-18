package com.artsiomhanchar.peopledbweb.web.controller;

import com.artsiomhanchar.peopledbweb.business.model.Person;
import com.artsiomhanchar.peopledbweb.business.service.PersonService;
import com.artsiomhanchar.peopledbweb.data.FileStorageRepository;
import com.artsiomhanchar.peopledbweb.data.PersonRepository;
import com.artsiomhanchar.peopledbweb.exception.StorageException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {

    public static final String DISPOSITION = """
            attachment; filename="%s"
            """;
    private PersonRepository personRepository;
    private FileStorageRepository fileStorageRepository;

    private PersonService personService;

    public PeopleController(PersonRepository personRepository, FileStorageRepository fileStorageRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
        this.personService = personService;
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

    @GetMapping("/images/{resource}")
    public ResponseEntity<Resource> getResource(@PathVariable String resource) {

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(DISPOSITION, resource))
                .body(fileStorageRepository.findByName(resource));
    }

    @PostMapping
    public String savePerson(Model model, @Valid Person person, Errors errors, @RequestParam("photoFileName") MultipartFile photoFile) throws IOException {
        log.info(person);
        log.info("FileName " + photoFile.getOriginalFilename());
        log.info("File Size " + photoFile.getSize());
        log.info("Errors "+ errors);

        if(!errors.hasErrors()) {
            try {
                personService.save(person, photoFile.getInputStream());

                return "redirect:people";
            } catch (StorageException e) {
                model.addAttribute("errorMsg", "System is currently unable to accept photo files at this time.");

                return "people";
            }
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
