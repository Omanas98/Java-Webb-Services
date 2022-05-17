package com.example.personsrest.domain;

import com.example.personsrest.remote.GroupRemote;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class PersonImpl implements Person {
    private String id;
    private String name;
    private int age;
    private String city;
    private boolean active;
    private List<String> groupList;

    private GroupRemote groupRemote;

    public PersonImpl(String name, int age, String city, List<String> groupList) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.city = city;
        this.groupList = groupList;
    }

    @Override
    public List<String> getGroups() {
        return this.groupList == null ? List.of() : this.groupList;
    }

    @Override
    public void addGroup(String groupId) {
        this.groupList.add(groupId);
    }

    @Override
    public void removeGroup(String groupId) {
        this.groupList.removeIf(g -> g.equals(groupId));
    }
}