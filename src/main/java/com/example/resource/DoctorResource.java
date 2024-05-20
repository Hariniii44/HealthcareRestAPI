/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author LENOVO
 */

import com.example.dao.DoctorDAO;
import com.example.model.Doctor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.example.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.WebApplicationException;


@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorResource.class);
    
    private DoctorDAO doctorDAO = new DoctorDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> getAllDoctors() {
        try {
            LOGGER.info("Retrieving all doctors");
            return doctorDAO.getAllDoctors();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all doctors: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all doctors", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor getDoctorById(@PathParam("id") int id) {
        LOGGER.info("Getting doctor by ID: {}", id);
        Doctor doctor = doctorDAO.getDoctorById(id);
        if (doctor != null) {
            return doctor;
        } else {
            throw new ResourceNotFoundException("Doctor with id " + id + " not found");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        try {
            LOGGER.info("Adding new doctor: {}", doctor);
            doctorDAO.addDoctor(doctor);
            LOGGER.info("Doctor added successfully.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("Error adding doctor: {}", e.getMessage());
            throw new WebApplicationException("Error adding doctor", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("id") int id, Doctor updatedDoctor) {
        try {
            LOGGER.info("Updating doctor with the id: {}", id);
            Doctor existingDoctor = doctorDAO.getDoctorById(id);
            if (existingDoctor != null) {
                LOGGER.info("Existing doctor found: {}", existingDoctor);
                updatedDoctor.setId(id);
                doctorDAO.updateDoctor(updatedDoctor);
                LOGGER.info("Doctor updated successfully");
                return Response.ok().build();
            } else {
                LOGGER.warn("Doctor with id {} not found", id);
                throw new ResourceNotFoundException("Doctor with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating doctor with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating doctor", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDoctor(@PathParam("id") int id) {
        LOGGER.info("Deleting doctor with id: {}", id);
        Doctor existingDoctor = doctorDAO.getDoctorById(id);
        if (existingDoctor == null) {
            LOGGER.error("Doctor with id {} not found", id);
            throw new ResourceNotFoundException("Doctor with id " + id + " not found");
        }
        try {
            doctorDAO.deleteDoctor(id);
            LOGGER.info("Doctor deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting doctor with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error deleting doctor", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

