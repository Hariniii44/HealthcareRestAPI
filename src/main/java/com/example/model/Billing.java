/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.model;

/**
 *
 * @author LENOVO
 */
public class Billing {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private String invoices;
    private String payments;
    private String outstandingBalances;
    
    public Billing() {
    }

    public Billing(int id, Patient patient, Doctor doctor, String invoices, String payments, String outstandingBalances) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.invoices = invoices;
        this.payments = payments;
        this.outstandingBalances = outstandingBalances;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getInvoices() {
        return invoices;
    }

    public void setInvoices(String invoices) {
        this.invoices = invoices;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public String getOutstandingBalances() {
        return outstandingBalances;
    }

    public void setOutstandingBalances(String outstandingBalances) {
        this.outstandingBalances = outstandingBalances;
    }
}

