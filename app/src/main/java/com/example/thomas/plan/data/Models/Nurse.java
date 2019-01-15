package com.example.thomas.plan.data.Models;

import com.example.thomas.plan.common.Enums;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Nurse {

    private String uniqueID;
    private String email;
    private String name;
    private String surname;
    private Enums.PartOfDay shift = Enums.PartOfDay.UNDEFINED;
    private String createdDate;
    private Enums.TypeOfGroup typeOfGroup = Enums.TypeOfGroup.UNDEFINED;
    private boolean inWork = false;
    private String nameOfImage;

    public Nurse(){

    }

    public Nurse(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public Enums.TypeOfGroup getTypeOfGroup() {
        return typeOfGroup;
    }

    public void setTypeOfGroup(Enums.TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public boolean isInWork() {
        return inWork;
    }

    public void setInWork(boolean inWork) {
        this.inWork = inWork;
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

    public Enums.PartOfDay getShift() {
        return shift;
    }

    public void setShift(Enums.PartOfDay shift) {
        this.shift = shift;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getNameAndSurname(){
        return name + " " + surname;
    }

    public String getNameOfImage() {
        return nameOfImage;
    }

    public void setNameOfImage(String nameOfImage) {
        this.nameOfImage = nameOfImage;
    }
}
