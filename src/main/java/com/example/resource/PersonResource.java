/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PersonDAO;
import com.example.model.Person;
import com.example.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import javax.ws.rs.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author LENOVO
 */
@Path("/persons")
public class PersonResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonResource.class);

    private PersonDAO personDAO = new PersonDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        try {
            LOGGER.info("Retrieving all persons...");
            return personDAO.getAllPersons();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all persons: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all persons", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPersonById(@PathParam("id") int id) {
        LOGGER.info("Getting user by ID: {}", id);
        Person person = personDAO.getPersonById(id);
        if (person != null) {
            return person;
        } else {
            throw new ResourceNotFoundException("Person with id " + id + " not found");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        try {
            LOGGER.info("Adding new person: {}", person);
            personDAO.addPerson(person);
            LOGGER.info("Person added successfully.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("Error adding person: {}", e.getMessage());
            throw new WebApplicationException("Error adding person", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") int id, Person updatedPerson) {
        try {
            LOGGER.info("Updating person with the id: {}", id);
            Person existingPerson = personDAO.getPersonById(id);
            if (existingPerson != null) {
                LOGGER.info("Existing person found: {}", existingPerson);
                updatedPerson.setId(id);
                personDAO.updatePerson(updatedPerson);
                LOGGER.info("Person updated successfully");
                return Response.ok().build();
            } else {
                LOGGER.warn("Person with id {} not found", id);
                throw new ResourceNotFoundException("Person with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating person with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating person", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        LOGGER.info("Deleting person with id: {}", id);
        Person existingPerson = personDAO.getPersonById(id);
        if (existingPerson == null) {
            LOGGER.error("Person with id {} not found", id);
            throw new ResourceNotFoundException("Person with id " + id + " not found");
        }
        try {
            personDAO.deletePerson(id);
            LOGGER.info("Person deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting person with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error deleting person", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
