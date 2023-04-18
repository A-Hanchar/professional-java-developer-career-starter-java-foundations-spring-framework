package com.artsiomhanchar.peopledbweb.business.service;

import com.artsiomhanchar.peopledbweb.business.model.Person;
import com.artsiomhanchar.peopledbweb.data.FileStorageRepository;
import com.artsiomhanchar.peopledbweb.data.PersonRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Transactional
    public void deleteAllById(Iterable<Long> ids) {
//        Iterable<Person> peopleToDelete = personRepository.findAllById(ids);
//        Stream<Person> peopleStream = StreamSupport
//                .stream(peopleToDelete.spliterator(), false);
//
//        Set<String> filenames = peopleStream
//                .map(Person::getPhotoFileName)
//                .collect(Collectors.toSet());

        Set<String> filenames = personRepository.findFilenamesByIds(ids);

        personRepository.deleteAllById(ids);
        fileStorageRepository.deleteAllByName(filenames);
    }
}
