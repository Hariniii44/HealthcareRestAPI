/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PersonDAO {
    private static List<Person> persons = new ArrayList<>();

    static {
        persons.add(new Person(1, "name1" , "0707878787", "address1"));
        persons.add(new Person(2, "name2" , "0705655656", "address2"));
    }
    
    public List<Person> getAllPersons() {
        return persons;
    }
    
    public Person getPersonById(int id) {
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        return null;
    }

    public void addPerson(Person person) {
        int newUserId = getNextPersonId();
        person.setId(newUserId);
        persons.add(person);
    }
    
    public void updatePerson(Person updatedPerson) {
        for (Person person : persons) {
            if (person.getId() == updatedPerson.getId()) {
                int index = persons.indexOf(person);
                persons.set(index, updatedPerson);
                System.out.println("Person updated: " + updatedPerson);
                break;
            }
        }
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
    }
    
//    private int getNextPersonId() {
//        return ++maxPersonId;
//    }

    private int getNextPersonId() {
            // Initialize maxUserId with a value lower than any possible userId
        int maxUserId = Integer.MIN_VALUE;

        // Iterate through the list to find the maximum userId
        for (Person person : persons) {
            int userId = person.getId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        // Increment the maximum userId to get the next available userId
        return maxUserId + 1;
    }
}


//        int maxId = Integer.MIN_VALUE;
//        for (Person person : persons) {
//            if (person.getId() > maxId) {
//                maxId = person.getId();
//            }
//        }
//        return maxId + 1;