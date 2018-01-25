package com.example.thomas.plan;

import java.util.UUID;

/**
 * Created by Thomas on 25.01.2018.
 */

public abstract class BasePerson {


    private String UniqueID;
    private String Name;
    private String Surname;

    public BasePerson( String name, String surname) {
        UniqueID = UUID.randomUUID().toString();
        Name = name;
        Surname = surname;
    }

    public BasePerson() {

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

}
