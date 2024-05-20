/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Doctor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class DoctorDAO {
    private static List<Doctor> doctors = new ArrayList<>();
    
    static {
        doctors.add(new Doctor(1, "doc 1" , "007079559", "address 1", "specialization 1 "));
        doctors.add(new Doctor(2, "name2" , "0705655656", "address2", "specialization 2"));
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }
    
    public Doctor getDoctorById(int id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    public void addDoctor(Doctor doctor) {
        doctor.setId(getNextDoctorId());
        doctors.add(doctor);
    }
    
    public void updateDoctor(Doctor updatedDoctor) {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == updatedDoctor.getId()) {
                int index = doctors.indexOf(doctor);
                doctors.set(index, updatedDoctor);
                System.out.println("Doctor updated: " + updatedDoctor);
                break;
            }
        }
    }

    public void deleteDoctor(int id) {
        doctors.removeIf(doctor -> doctor.getId() == id);
    }

    private int getNextDoctorId() {
        int maxId = Integer.MIN_VALUE;
        for (Doctor doctor : doctors) {
            if (doctor.getId() > maxId) {
                maxId = doctor.getId();
            }
        }
        return maxId + 1;
    }
}
