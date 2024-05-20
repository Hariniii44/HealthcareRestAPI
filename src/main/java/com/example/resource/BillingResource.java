/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

import com.example.dao.BillingDAO;
import com.example.model.Billing;
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

@Path("/billing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillingResource.class);
    private BillingDAO billingDAO = new BillingDAO();

    @GET
    public List<Billing> getAllBilling() {
        try {
            LOGGER.info("Retrieving all billings");
            return billingDAO.getAllBillings();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all billings: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all billings", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    public Response getBillingById(@PathParam("id") int id) {
        LOGGER.info("Getting billing by ID: {}", id);
        Billing billing = billingDAO.getBillingById(id);
        if (billing != null) {
            return Response.ok(billing).build();
        } else {
            LOGGER.warn("Billing with id {} not found", id);
            throw new ResourceNotFoundException("Billing with id " + id + " not found");
        }
    }

    @POST
    public Response addBilling(Billing billing) {
        try {
            LOGGER.info("Adding new billing: {}", billing);
            billingDAO.addBilling(billing);
            LOGGER.info("Billing added successfully.");
            return Response.status(Response.Status.CREATED).entity(billing).build();
        } catch (Exception e) {
            LOGGER.error("Error adding billing: {}", e.getMessage());
            throw new WebApplicationException("Error adding billing", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateBilling(@PathParam("id") int id, Billing updatedBilling) {
        try {
            LOGGER.info("Updating billing with the id: {}", id);
            Billing existingBilling = billingDAO.getBillingById(id);
            if (existingBilling != null) {
                LOGGER.info("Existing billing found: {}", existingBilling);
                updatedBilling.setId(id);
                billingDAO.updateBilling(updatedBilling);
                LOGGER.info("Billing updated successfully");
                return Response.ok(updatedBilling).build();
            } else {
                LOGGER.warn("Billing with id {} not found", id);
                throw new ResourceNotFoundException("Billing with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating billing with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating billing", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBilling(@PathParam("id") int id) {
        LOGGER.info("Deleting billing with id: {}", id);
        billingDAO.deleteBilling(id);
        LOGGER.info("Billing deleted successfully.");
        return Response.noContent().build();
    }
}

