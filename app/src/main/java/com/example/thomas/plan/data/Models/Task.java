package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Task {

    private final String uniqueID;
    private String name;
    //public String pathToImg;
    private boolean passed;

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

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
