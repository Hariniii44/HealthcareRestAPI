/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class PatientDAO {
    private static List<Patient> patients = new ArrayList<>();
    
    static {
        patients.add(new Patient(1, "name" , "0707878787", "address1", "history 1", "status 1"));
        patients.add(new Patient(2, "patient2" , "070579559", "address 2", "history 2", "status 2"));
    }

    public List<Patient> getAllPatients() {
        return patients;
    }
    
    public Patient getPatientById(int id) {
        for (Patient patient : patients) {
            if (patient.getId() == id) {
                return patient;
            }
        }
        return null;
    }

    public void addPatient(Patient patient) {
        patient.setId(getNextPatientId());
        patients.add(patient);
    }
    
    public void updatePatient(Patient updatedPatient) {
        for (Patient patient : patients) {
            if (patient.getId() == updatedPatient.getId()) {
                int index = patients.indexOf(patient);
                patients.set(index, updatedPatient);
                System.out.println("Patient updated: " + updatedPatient);
                break;
            }
        }
    }

    public void deletePatient(int id) {
        patients.removeIf(patient -> patient.getId() == id);
    }

    private int getNextPatientId() {
        int maxUserId = Integer.MIN_VALUE;

        for (Patient patient : patients) {
            int userId = patient.getId();
            if (userId > maxUserId) {
                maxUserId = userId;
            }
        }

        //Increment the maximum userId to get the next available userId
        return maxUserId + 1;
    }
}