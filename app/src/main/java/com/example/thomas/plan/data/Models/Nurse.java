package com.example.thomas.plan.data.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.Local.Converters;

/**
 * Created by Tomas on 05-Mar-18.
 */
//todo pridat atribut shift a zkonvertovat

@Entity(tableName = "nurse")
public class Nurse {

    @NonNull
    @PrimaryKey()
    public String UniqueID;

    @ColumnInfo(name = "first_name")
    public String Name;

    @ColumnInfo(name = "sur_name")
    public String Surname;


    public Nurse(String name, String surname) {
        Name = name;
        Surname = surname;
    }

    public Nurse() {

    }


    public String getUniqueID() {
        return UniqueID;
    }


    public void setUniqueID(@NonNull String uniqueID) {
        UniqueID = uniqueID;
    }


    public String getName() {
        return Name;
    }


    public void setName(String name) {
        Name = name;
    }


    public String getSurname() {
        return Surname;
    }


    public void setSurname(String surname) {
        Surname = surname;
    }


}
