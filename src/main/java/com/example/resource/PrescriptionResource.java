/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.PrescriptionDAO;
import com.example.model.Prescription;
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

@Path("/prescriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrescriptionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrescriptionResource.class);
    
    private PrescriptionDAO prescriptionDAO = new PrescriptionDAO();

    @GET
    public List<Prescription> getAllPrescriptions() {
        try {
            LOGGER.info("Retrieving all prescriptions");
            return prescriptionDAO.getAllPrescriptions();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all prescriptions: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all prescriptions", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getPrescriptionById(@PathParam("id") int id) {
        LOGGER.info("Getting prescription by ID: {}", id);
        Prescription prescription = prescriptionDAO.getPrescriptionById(id);
        if (prescription != null) {
            return Response.ok(prescription).build();
        } else {
            LOGGER.warn("Prescription with id {} not found", id);
            throw new ResourceNotFoundException("Prescription with id " + id + " not found");
        }
    }

    @POST
    public Response addPrescription(Prescription prescription) {
        try {
            LOGGER.info("Adding new prescription: {}", prescription);
            prescriptionDAO.addPrescription(prescription);
            LOGGER.info("Prescription added successfully.");
            return Response.status(Response.Status.CREATED).entity(prescription).build();
        } catch (Exception e) {
            LOGGER.error("Error adding prescription: {}", e.getMessage());
            throw new WebApplicationException("Error adding prescription", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updatePrescription(@PathParam("id") int id, Prescription updatedPrescription) {
        try {
            LOGGER.info("Updating prescription with the id: {}", id);
            Prescription existingPrescription = prescriptionDAO.getPrescriptionById(id);
            if (existingPrescription != null) {
                LOGGER.info("Existing prescription found: {}", existingPrescription);
                updatedPrescription.setId(id);
                prescriptionDAO.updatePrescription(updatedPrescription);
                LOGGER.info("Prescription updated successfully");
                return Response.ok(updatedPrescription).build();
            } else {
                LOGGER.warn("Prescription with id {} not found", id);
                throw new ResourceNotFoundException("Prescription with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating prescription with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating prescription", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletePrescription(@PathParam("id") int id) {
        LOGGER.info("Deleting prescription with id: {}", id);
        prescriptionDAO.deletePrescription(id);
        LOGGER.info("Prescription deleted successfully.");
        return Response.noContent().build();
    }
}
