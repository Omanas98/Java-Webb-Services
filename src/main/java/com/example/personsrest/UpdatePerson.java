package com.example.personsrest;

import lombok.Value;

@Value
public class UpdatePerson {
    String name;
    String city;
    int age;
}