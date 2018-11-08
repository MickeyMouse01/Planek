package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.UUID;

public class Client implements Comparable<Client>{

    private final String uniqueID;
    private TypeOfGroup typeOfGroup;
    private String firstName;
    private String surname;
    private String nurseID;
    private String planId;
    private String createdDate;
    private String password;
    private String username;

    public Client(String name, String surname, TypeOfGroup typeOfGroup) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        firstName = name;
        this.surname = surname;
        this.typeOfGroup = typeOfGroup;
        this.uniqueID = UUID.randomUUID().toString();
        this.nurseID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Client() {
        this.uniqueID = UUID.randomUUID().toString();
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNurseID() {
        return nurseID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setTypeOfGroup(TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public TypeOfGroup getTypeOfGroup() {
        return typeOfGroup;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public int compareTo(@NonNull Client o) {
        return this.getSurname().compareToIgnoreCase(o.getSurname());
    }
}
