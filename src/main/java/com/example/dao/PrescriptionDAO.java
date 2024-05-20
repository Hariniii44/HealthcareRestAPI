/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Patient;
import com.example.model.Prescription;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PrescriptionDAO {
    private static List<Prescription> prescriptions = new ArrayList<>();
    private static int maxPrescriptionId = 0;
    
    static {
        prescriptions.add(new Prescription(new Patient(1, "name" , "0707878787", "address1", "history 1", "status 1"), "medication 1", "dose 1", "instructions 1", "duration 1"));
        prescriptions.add(new Prescription(new Patient(2, "patient2" , "070579559", "address 2", "history 2", "status 2"), "medicationnn2", "doseee2", "instruuuv2", "duration 2"));
    }

    // Method to retrieve all prescriptions
    public List<Prescription> getAllPrescriptions() {
        return prescriptions;
    }
    
    // Method to retrieve a prescription by ID
    public Prescription getPrescriptionById(int id) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == id) {
                return prescription;
            }
        }
        return null; // Return null if prescription not found
    }

    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
//        prescription.setId(getNextPrescriptionId());
        int newPrescriptionId = getNextPrescriptionId();
        prescription.setId(newPrescriptionId);
        prescriptions.add(prescription);
    }
    
    // Method to update an existing prescription
    public void updatePrescription(Prescription updatedPrescription) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getId() == updatedPrescription.getId()) {
                int index = prescriptions.indexOf(prescription);
                prescriptions.set(index, updatedPrescription);
                return; // Exit loop once prescription is updated
            }
        }
    }

    // Method to delete a prescription by ID
    public void deletePrescription(int id) {
        prescriptions.removeIf(prescription -> prescription.getId() == id);
    }

    // Helper method to generate unique prescription IDs
    private int getNextPrescriptionId() {
        return ++maxPrescriptionId;
    }
}
