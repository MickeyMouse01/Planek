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

public class Client  {

    private FirebaseAuth mAuth;

    public String uniqueID;
    public TypeOfGroup typeOfGroup;
    public String firstName;
    public String surname;
    public String nurseID;

    public Client(String name, String surname, TypeOfGroup typeOfGroup) {
        mAuth = FirebaseAuth.getInstance();
        firstName = name;
        this.surname = surname;
        this.typeOfGroup = typeOfGroup;
        this.uniqueID = UUID.randomUUID().toString();
        this.nurseID = mAuth.getCurrentUser().getUid();
    }
    public Client (){
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

    public void setNurseID(String nurseID) {
        this.nurseID = nurseID;
    }

    public String getUniqueID() {
        return uniqueID;
    }


    public void setUniqueID(@NonNull String uniqueID) {
        uniqueID = uniqueID;
    }


    public void setTypeOfGroup(TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public String getTypeOfGroup() {
        return typeOfGroup.name();
    }

}
