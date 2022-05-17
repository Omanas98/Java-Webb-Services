package com.example.personsrest;

import com.example.personsrest.domain.*;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private PersonRepository personRepository;
    private GroupRemote groupRemote;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPerson(String id) {
        return personRepository.findById(id);
    }

    public Person createPerson(CreatePerson createPerson) {
        PersonImpl person = new PersonImpl(
                createPerson.getName(),
                createPerson.getAge(),
                createPerson.getCity(),
                new ArrayList<>()
        );
        return personRepository.save(person);
    }

    public Person updatePerson(String id, String name, String city, int age) {
        Person person = personRepository.findById(id).get();
        person.setName(name);
        person.setCity(city);
        person.setAge(age);
        return personRepository.save(person);
    }

    public void deletePersonId(String id) {
        personRepository.delete(id);
    }

    public Person addGroupPerson(String id, String groupName) {
        Person person = personRepository.findById(id).orElse(null);
        String groupId = groupRemote.createGroup(groupName);
        person.addGroup(groupId);
        return personRepository.save(person);
    }

    public Person removeGroupFromPerson(String id, String groupId) {
        Person person = personRepository.findById(id).get();
        if (groupId.length() >= 30) {
            person.removeGroup(groupId);
        } else {
            person.getGroups().removeIf(g -> groupRemote.getNameById(g).equals(groupId));
        }
        return personRepository.save(person);
    }

    public Page<Person> getAllNamesAndCities(String search, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return personRepository.findAllByNameContainingOrCityContaining(search, search, pageable);
    }
}