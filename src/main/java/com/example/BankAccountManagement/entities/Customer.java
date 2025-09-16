package com.example.BankAccountManagement.entities;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String fullName;
    private String gender;
    private String email;

    public Customer() {}

    public Customer(String fullName, String gender, String email) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
