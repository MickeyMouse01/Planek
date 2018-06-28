package com.example.thomas.plan.data.Models;

/**
 * Created by Thomas on 25.01.2018.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;
import com.example.thomas.plan.data.Local.Converters.TypeOfGroupConverter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.UUID;

public class Client {

    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
        firstName = name;
        this.surname = surname;
        this.typeOfGroup = typeOfGroup;
        this.uniqueID = UUID.randomUUID().toString();
        this.nurseID = mAuth.getCurrentUser().getUid();
        this.planId = "null";
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

    public String getTypeOfGroup() {
        return typeOfGroup.name();
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
