package com.example.thomas.plan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by Thomas on 25.01.2018.
 */

public abstract class BasePerson implements Parcelable {


    private String UniqueID;
    private String Name;
    private String Surname;

    public BasePerson( String name, String surname) {
        UniqueID = UUID.randomUUID().toString();
        Name = name;
        Surname = surname;
    }
    public BasePerson () {
    }

    protected BasePerson(Parcel in) {
        UniqueID = in.readString();
        Name = in.readString();
        Surname = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UniqueID);
        dest.writeString(Name);
        dest.writeString(Surname);
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
