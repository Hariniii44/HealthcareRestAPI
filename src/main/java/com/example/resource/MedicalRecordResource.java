/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.MedicalRecordDAO;
import com.example.model.MedicalRecord;
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

@Path("/medicalrecords")
public class MedicalRecordResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordResource.class);
    
    private MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        try {
            LOGGER.info("Retrieving all medical records");
            return medicalRecordDAO.getAllMedicalRecords();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all medical records: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all medical records", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MedicalRecord getMedicalRecordById(@PathParam("id") int id) {
        LOGGER.info("Getting medical record by ID: {}", id);
        MedicalRecord medicalRecord = medicalRecordDAO.getMedicalRecordById(id);
        if (medicalRecord != null) {
            return medicalRecord;
        } else {
            throw new ResourceNotFoundException("Medical record with id " + id + " not found");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            LOGGER.info("Adding new medical record: {}", medicalRecord);
            medicalRecordDAO.addMedicalRecord(medicalRecord);
            LOGGER.info("Medical record added successfully.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("Error adding medical record: {}", e.getMessage());
            throw new WebApplicationException("Error adding medical record", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("id") int id, MedicalRecord updatedMedicalRecord) {
        try {
            LOGGER.info("Updating medical record with the id: {}", id);
            MedicalRecord existingMedicalRecord = medicalRecordDAO.getMedicalRecordById(id);
            if (existingMedicalRecord != null) {
                LOGGER.info("Existing medical record found: {}", existingMedicalRecord);
                updatedMedicalRecord.setId(id);
                medicalRecordDAO.updateMedicalRecord(updatedMedicalRecord);
                LOGGER.info("Medical record updated successfully");
                return Response.ok().build();
            } else {
                LOGGER.warn("Medical record with id {} not found", id);
                throw new ResourceNotFoundException("Medical record with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating medical record with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating medical record", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMedicalRecord(@PathParam("id") int id) {
        LOGGER.info("Deleting medical record with id: {}", id);
        MedicalRecord existingMedicalRecord = medicalRecordDAO.getMedicalRecordById(id);
        if (existingMedicalRecord == null) {
            LOGGER.error("Medical record with id {} not found", id);
            throw new ResourceNotFoundException("Medical record with id " + id + " not found");
        }
        try {
            medicalRecordDAO.deleteMedicalRecord(id);
            LOGGER.info("Medical record deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting medical record with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error deleting medical record", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
