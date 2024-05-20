/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.resource;

/**
 *
 * @author LENOVO
 */
import com.example.dao.AppointmentDAO;
import com.example.model.Appointment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.example.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.WebApplicationException;

@Path("/appointments")
public class AppointmentResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentResource.class);

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAllAppointments() {
        try {
            LOGGER.info("Retrieving all appointments");
            return appointmentDAO.getAllAppointments();
        } catch (Exception e) {
            LOGGER.error("Error retrieving all appointments: {}", e.getMessage());
            throw new WebApplicationException("Error retrieving all appointments", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Appointment getAppointmentById(@PathParam("id") int id) {
        LOGGER.info("Getting appointment by ID: {}", id);
        Appointment appointment = appointmentDAO.getAppointmentById(id);
        if (appointment != null) {
            return appointment;
        } else {
            throw new ResourceNotFoundException("Appointment with id " + id + " not found");
        }
    }

    @GET
    @Path("/doctor/{doctorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAppointmentsByDoctor(@PathParam("doctorId") int doctorId) {
        LOGGER.info("Getting appointments by doctor ID: {}", doctorId);
        List<Appointment> appointments = appointmentDAO.getAppointmentsByDoctor(doctorId);
        if (appointments != null && !appointments.isEmpty()) {
            return appointments;
        } else {
            throw new ResourceNotFoundException("Appointments with doctor id " + doctorId + " not found");
        }
    }

    @GET
    @Path("/patient/{patientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAppointmentsByPatient(@PathParam("patientId") int patientId) {
        LOGGER.info("Getting appointments by patient ID: {}", patientId);
        List<Appointment> appointments = appointmentDAO.getAppointmentsByPatient(patientId);
        if (appointments != null && !appointments.isEmpty()) {
            return appointments;
        } else {
            throw new ResourceNotFoundException("Appointments with patient id " + patientId + " not found");
        }
    }

    @GET
    @Path("/time/{time}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAppointmentsByTime(@PathParam("time") String time) {
        LOGGER.info("Getting appointments by time: {}", time);
        List<Appointment> appointments = appointmentDAO.getAppointmentsByTime(time);
        if (appointments != null && !appointments.isEmpty()) {
            return appointments;
        } else {
            throw new ResourceNotFoundException("Appointments at time " + time + " not found");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
        try {
            LOGGER.info("Adding new appointment: {}", appointment);
            appointmentDAO.addAppointment(appointment);
            LOGGER.info("Appointment added successfully.");
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            LOGGER.error("Error adding appointment: {}", e.getMessage());
            throw new WebApplicationException("Error adding appointment", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") int id, Appointment updatedAppointment) {
        try {
            LOGGER.info("Updating appointment with the id: {}", id);
            Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
            if (existingAppointment != null) {
                LOGGER.info("Existing appointment found: {}", existingAppointment);
                updatedAppointment.setId(id);
                appointmentDAO.updateAppointment(updatedAppointment);
                LOGGER.info("Appointment updated successfully");
                return Response.ok().build();
            } else {
                LOGGER.warn("Appointment with id {} not found", id);
                throw new ResourceNotFoundException("Appointment with id " + id + " not found");
            }
        } catch (WebApplicationException e) {
            LOGGER.error("Error updating appointment with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error updating appointment", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAppointment(@PathParam("id") int id) {
        LOGGER.info("Deleting appointment with id: {}", id);
        Appointment existingAppointment = appointmentDAO.getAppointmentById(id);
        if (existingAppointment == null) {
            LOGGER.error("Appointment with id {} not found", id);
            throw new ResourceNotFoundException("Appointment with id " + id + " not found");
        }
        try {
            appointmentDAO.deleteAppointment(id);
            LOGGER.info("Appointment deleted successfully.");
            return Response.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting appointment with id {}: {}", id, e.getMessage());
            throw new WebApplicationException("Error deleting appointment", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}

