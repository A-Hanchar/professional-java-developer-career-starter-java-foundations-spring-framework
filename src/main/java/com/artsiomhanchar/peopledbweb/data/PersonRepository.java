package com.artsiomhanchar.peopledbweb.data;

import com.artsiomhanchar.peopledbweb.business.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
