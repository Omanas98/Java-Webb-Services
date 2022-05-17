package com.example.personsrest;

import com.example.personsrest.domain.Person;
import lombok.Value;

import java.util.List;

@Value
public class PersonPage {
    Long pageSize;
    int pageNumber;
    String search;
    List<Person> personList;
}
