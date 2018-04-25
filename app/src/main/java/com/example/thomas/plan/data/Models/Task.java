package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Task {

    public final String uniqueID;
    public String name;
    public String pathToImg;
    public boolean isDone;


    public Task() {
        this.uniqueID = UUID.randomUUID().toString();
    }

    @NonNull
    public String getUniqueID() {
        return uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
