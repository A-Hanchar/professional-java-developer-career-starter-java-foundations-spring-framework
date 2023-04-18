package com.artsiomhanchar.peopledbweb.business.service;

import com.artsiomhanchar.peopledbweb.business.model.Person;
import com.artsiomhanchar.peopledbweb.data.FileStorageRepository;
import com.artsiomhanchar.peopledbweb.data.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final FileStorageRepository fileStorageRepository;

    public PersonService(PersonRepository personRepository, FileStorageRepository fileStorageRepository) {
        this.personRepository = personRepository;
        this.fileStorageRepository = fileStorageRepository;
    }

    @Transactional
    public Person save(Person person, InputStream photoStream) {
        Person savedPerson = personRepository.save(person);
        fileStorageRepository.save(person.getPhotoFileName(), photoStream);

        return savedPerson;
    }

    public Optional<Person> findById(Long aLong) {
        return personRepository.findById(aLong);
    }

    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        personRepository.deleteAllById(longs);
    }
}
