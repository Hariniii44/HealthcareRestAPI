/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.MedicalRecord;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */

public class MedicalRecordDAO {
    private static List<MedicalRecord> medicalRecords = new ArrayList<>();

    static {
        // Add example medical records
        medicalRecords.add(new MedicalRecord(1, new Patient(1, "name" , "0707878787", "address1", "history 1", "status 1"), "Patient 1 condition", "treatment 1"));
        medicalRecords.add(new MedicalRecord(2, new Patient(2, "patient2" , "070579559", "address 2", "history 2", "status 2"), "Patient 2 condition", "treatment 2"));
    }
    
    // Method to retrieve all medical records
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecords;
    }
    
    // Method to retrieve a medical record by ID
    public MedicalRecord getMedicalRecordById(int id) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == id) {
                return medicalRecord;
            }
        }
        return null; // Return null if medical record not found
    }

    //Method to retrieve appointments by patient
    public List<MedicalRecord> getMedicalRecordByPatient(int patientId) {
        List<MedicalRecord> appointmentsByPatient = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getPatientId() == patientId) {
                appointmentsByPatient.add(medicalRecord);
            }
        }
        return appointmentsByPatient;
    }
    
    // Method to add a new medical record
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecord.setId(getNextMedicalRecordId());
        medicalRecords.add(medicalRecord);
    }
    
    // Method to update an existing medical record
    public void updateMedicalRecord(MedicalRecord updatedMedicalRecord) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getId() == updatedMedicalRecord.getId()) {
                int index = medicalRecords.indexOf(medicalRecord);
                medicalRecords.set(index, updatedMedicalRecord);
                return; // Exit loop once medical record is updated
            }
        }
    }

    // Method to delete a medical record by ID
    public void deleteMedicalRecord(int id) {
        medicalRecords.removeIf(medicalRecord -> medicalRecord.getId() == id);
    }
    
    private int getNextMedicalRecordId() {
        int maxRecordId = Integer.MIN_VALUE;

        // Iterate through the list to find the maximum recordId
        for (MedicalRecord medicalRecord : medicalRecords) {
            int recordId = medicalRecord.getId();
            if (recordId > maxRecordId) {
                maxRecordId = recordId;
            }
        }

        // Increment the maximum userId to get the next available userId
        return maxRecordId + 1;
    }
}