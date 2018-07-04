package com.example.thomas.plan.data.Models;

import com.example.thomas.plan.Common.Enums;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Nurse {

    private  String uniqueID;
    private String email;
    private String password; //todo pridat nejaky hash
    private String name;
    private String surname;
    private Enums.Shift shift;
    private String createdDate;

    public Nurse(){

    }

    public Nurse(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Enums.Shift getShift() {
        return shift;
    }

    public void setShift(Enums.Shift shift) {
        this.shift = shift;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
