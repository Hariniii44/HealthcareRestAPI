/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PatientDAO;
import com.example.model.Patient;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.example.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.WebApplicationException;



/**
 *
 * @author LENOVO
 */

@Path("/patients")
public class PatientResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);
    private PatientDAO patientDAO = new PatientDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients() {
        try {
            LOGGER.info("Retrieving all patients");
            return patientDAO.getAllPatients();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all patients: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all patients", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Patient getPatientById(@PathParam("id") int id) {
        LOGGER.info("Getting patient by ID: {}", id);
        Patient patient = patientDAO.getPatientById(id);
        if (patient != null) {
            return patient;
        } else {
            throw new ResourceNotFoundException("Patient with id " + id + " not found");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        try {
            LOGGER.info("Adding new patient: {}", patient);
            patientDAO.addPatient(patient);
            LOGGER.info("Patient added successfully.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("Error adding patient: {}", e.getMessage());
            throw new WebApplicationException("Error adding patient", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") int id, Patient updatedPatient) {
        try {
            LOGGER.info("Updating patient with the id: {}", id);
            Patient existingPatient = patientDAO.getPatientById(id);
            if (existingPatient != null) {
                LOGGER.info("Existing patient found: {}", existingPatient);
                updatedPatient.setId(id);
                patientDAO.updatePatient(updatedPatient);
                LOGGER.info("Patient updated successfully");
                return Response.ok().build();
            } else {
                LOGGER.warn("Patient with id {} not found", id);
                throw new ResourceNotFoundException("Patient with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating patient with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating patient", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") int id) {
        LOGGER.info("Deleting patient with id: {}", id);
        Patient existingPatient = patientDAO.getPatientById(id);
        if (existingPatient == null) {
            LOGGER.error("Patient with id {} not found", id);
            throw new ResourceNotFoundException("Patient with id " + id + " not found");
        }
        try {
            patientDAO.deletePatient(id);
            LOGGER.info("Patient deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting patient with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error deleting patient", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

