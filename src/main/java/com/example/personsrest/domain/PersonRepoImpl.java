package com.example.personsrest.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepoImpl implements PersonRepository{

    ArrayList<Person> personArrayList = new ArrayList<>();

    @Override
    public Optional<Person> findById(String id) {
        for (Person p : personArrayList){
            if (p.getId().equals(id)){
                Optional<Person> person = Optional.of(p);
                return person;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Person> findAll() {
        return personArrayList;
    }

    @Override
    public Page<Person> findAllByNameContainingOrCityContaining(String name, String city, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Person save(Person person) {
        personArrayList.add(person);
        return person;
    }

    @Override
    public void delete(String id) {
        personArrayList.remove(id);
    }
}