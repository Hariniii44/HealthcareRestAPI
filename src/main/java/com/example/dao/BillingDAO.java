/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dao;

import com.example.model.Billing;
import com.example.model.Doctor;
import com.example.model.Patient;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class BillingDAO {
    private static List<Billing> billings = new ArrayList<>();
    
    static {
        billings.add(new Billing(1, new Patient(1, "name" , "0707878787", "address1", "history 1", "status 1"), new Doctor(1, "doc 1" , "007079559", "address 1", "specialization 1 "), "invoice 1", "amount 1", "balance 1"));
        billings.add(new Billing(2, new Patient(2, "patient2" , "070579559", "address 2", "history 2", "status 2"), new Doctor(2, "name2" , "0705655656", "address2", "specialization 2"), "invoice 2", "amount 2", "balance 2"));
    }

    //Method to retrieve all billings
    public List<Billing> getAllBillings() {
        return billings;
    }
    
    //Method to retrieve a billing by ID
    public Billing getBillingById(int id) {
        for (Billing billing : billings) {
            if (billing.getId() == id) {
                return billing;
            }
        }
        return null; //Return null if billing not found
    }

    //Method to add a new billing
    public void addBilling(Billing billing) {
        billing.setId(getNextBillingId());
        billings.add(billing);
    }
    
    // Method to update an existing billing
    public void updateBilling(Billing updatedBilling) {
        for (Billing billing : billings) {
            if (billing.getId() == updatedBilling.getId()) {
                int index = billings.indexOf(billing);
                billings.set(index, updatedBilling);
                return; //Exit loop once billing is updated
            }
        }
    }

    //Method to delete a billing by ID
    public void deleteBilling(int id) {
        billings.removeIf(billing -> billing.getId() == id);
    }
    
    private int getNextBillingId() {
        int maxBillingId = Integer.MIN_VALUE;

        for (Billing billing : billings) {
            int billingId = billing.getId();
            if (billingId > maxBillingId) {
                maxBillingId = billingId;
            }
        }

        // Increment the maxBillingId to get the next available maxBillingId
        return maxBillingId + 1;
    }
}