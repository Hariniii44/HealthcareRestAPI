/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Appointment;
import com.example.model.Doctor;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */

public class AppointmentDAO {

    private static List<Appointment> appointments = new ArrayList<>();

    static {
        appointments.add(new Appointment(1, "2024-05-10", "10:00 AM", new Patient(1, "name" , "0707878787", "address1", "history 1", "status 1"), new Doctor(1, "doc 1" , "007079559", "address 1", "specialization 1 ")));
        appointments.add(new Appointment(2, "2024-05-12", "11:00 AM", new Patient(2, "patient2" , "070579559", "address 2", "history 2", "status 2"), new Doctor(2, "name2" , "0705655656", "address2", "specialization 2")));
    }

    //Method to retrieve all appointments
    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    //Method to retrieve an appointment by ID
    public Appointment getAppointmentById(int id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == id) {
                return appointment;
            }
        }
        return null; // Return null if appointment not found
    }

    // Method to retrieve appointments by doctor
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        List<Appointment> appointmentsByDoctor = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId() == doctorId) {
                appointmentsByDoctor.add(appointment);
            }
        }
        return appointmentsByDoctor;
    }

    //Method to retrieve appointments by patient
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        List<Appointment> appointmentsByPatient = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientId() == patientId) {
                appointmentsByPatient.add(appointment);
            }
        }
        return appointmentsByPatient;
    }

        //Method to retrieve appointments by time
    public List<Appointment> getAppointmentsByTime(String time) {
        List<Appointment> appointmentsByTime = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getTime().equals(time)) {
                appointmentsByTime.add(appointment);
            }
        }
        return appointmentsByTime;
    }

    // Method to add a new appointment
    public void addAppointment(Appointment appointment) {
//        appointment.setId(getNextAppointmentId());
        int newAppointmentId = getNextAppointmentId();
        appointment.setId(newAppointmentId);
        appointments.add(appointment);
    }

    //Method to update an existing appointment
    public void updateAppointment(Appointment updatedAppointment) {
        for (Appointment appointment : appointments) {
            if (appointment.getId() == updatedAppointment.getId()) {
                int index = appointments.indexOf(appointment);
                appointments.set(index, updatedAppointment);
                return; // Exit loop once appointment is updated
            }
        }
    }

    //Method to delete an appointment by ID
    public void deleteAppointment(int id) {
        appointments.removeIf(appointment -> appointment.getId() == id);
    }

    //make a unique id for each appointment that is added
    public int getNextAppointmentId() {
        int maxAppointmentId = Integer.MIN_VALUE;

        for (Appointment appointment : appointments) {
            int appointmentId = appointment.getId();
            if (appointmentId > maxAppointmentId) {
                maxAppointmentId = appointmentId;
            }
        }

        // Increment the maximum maxAppointmentId to get the next available maxAppointmentId
        return maxAppointmentId + 1;
    }
}
