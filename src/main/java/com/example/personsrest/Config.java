package com.example.personsrest;

import com.example.personsrest.domain.PersonRepoImpl;
import com.example.personsrest.domain.PersonRepository;
import com.example.personsrest.remote.GroupRemote;
import com.example.personsrest.remote.GroupRemoteImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    /* Explicitly declaring our Beans within our configuration
    Basically indicating that these classes been called within the method
    are essential and needs to be included
    */
    @Bean
    public GroupRemote groupRemote() {
        return new GroupRemoteImpl();
    }

    @Bean
    public PersonRepository personRepository() {
        return new PersonRepoImpl();
    }
}
