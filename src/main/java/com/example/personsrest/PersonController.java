package com.example.personsrest;

import com.example.personsrest.domain.CreatePerson;
import com.example.personsrest.domain.Person;
import com.example.personsrest.domain.PersonDTO;
import com.example.personsrest.remote.GroupRemote;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
@AllArgsConstructor
@Service
@Repository
public class PersonController {

    private PersonService personService;
    private GroupRemote groupRemote;
    private PersonDTO personDTO;

    // Method that wants all persons returned, and they need to contain the parameters specified
    @GetMapping
    public List<PersonDTO> getAllPersons(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (search == null || search.equals("")) {
            return personService.getAllPersons().stream().map(this::toDTO).collect(Collectors.toList());
        }
        Page<Person> page = personService.getAllNamesAndCities(search, pageNumber, pageSize);
        List<Person> list = page.getContent();
        return list.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<PersonDTO> getPerson(@PathVariable("id") String id) {
        return personService.getPerson(id).map(this::toDTO);
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody CreatePerson createPerson) {
        return toDTO(personService.createPerson(createPerson));
    }

    @PutMapping("/{id}")
    public PersonDTO updatePerson(@PathVariable("id") String id, @RequestBody UpdatePerson updatePerson) {
        return toDTO(personService.updatePerson(
                id,
                updatePerson.getName(),
                updatePerson.getCity(),
                updatePerson.getAge()
        ));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable("id") String id) {
        personService.deletePersonId(id);
    }

    @PutMapping("/{id}/addGroup/{groupName}")
    public PersonDTO addGroupToPerson(@PathVariable("id") String id,
                                      @PathVariable("groupName") String groupName) {
        return toDTO(personService.addGroupPerson(id, groupName));
    }

    @DeleteMapping("/{id}/removeGroup/{groupId}")
    public PersonDTO removeGroupFromPerson(@PathVariable("id") String id,
                                           @PathVariable("groupId") String groupId) {
        return personDTO;
    }

    private PersonDTO toDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getName(),
                person.getCity(),
                person.getAge(),
                person.getGroups().stream().map(name -> groupRemote.getNameById(name))
                        .collect(Collectors.toList()));
    }
}

