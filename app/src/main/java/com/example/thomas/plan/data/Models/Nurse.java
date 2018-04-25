package com.example.thomas.plan.data.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.Local.Converters;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Nurse {

    public final String uniqueID;
    public String name;
    public String surname;

    public Nurse(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.uniqueID = UUID.randomUUID().toString();
    }

    public String getUniqueID() {
        return uniqueID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        surname = surname;
    }


}
