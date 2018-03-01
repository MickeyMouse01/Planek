package com.example.thomas.plan.data;

/**
 * Created by Thomas on 25.01.2018.
 */
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;


public class Client  {

     @PrimaryKey()
    private String UniqueID;

     @ColumnInfo(name = "type_of_group")
    private TypeOfGroup typeOfGroup;

     @ColumnInfo(name = "first_name")
    private String Name;

     @ColumnInfo(name = "sur_name")
    private String Surname;


    public Client(String name, String surname, TypeOfGroup typeOfGroup) {
        Name = name;
        Surname = surname;
        this.typeOfGroup = typeOfGroup;
    }


    public String getUniqueID() {
        return UniqueID;
    }


    public void setUniqueID(String uniqueID) {
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


    public void setTypeOfGroup(TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public TypeOfGroup getTypeOfGroup() {
        return typeOfGroup;
    }

}
