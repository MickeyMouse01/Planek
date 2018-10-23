package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Task {

    private final String uniqueID;
    private String name;
    private boolean passed;
    private String createdDate;
    private String idOfPlan;
    private Enums.PartOfDay partOfDay = Enums.PartOfDay.UNDEFINED;

    public Task(String name) {
        this.name = name;
        this.uniqueID = UUID.randomUUID().toString();
    }
    public Task() {
        this.uniqueID = UUID.randomUUID().toString();
    }

    @NonNull
    public String getUniqueID() {
        return uniqueID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIdOfPlan() {
        return idOfPlan;
    }

    public void setIdOfPlan(String idOfPlan) {
        this.idOfPlan = idOfPlan;
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

    public Enums.PartOfDay getPartOfDay() {
        return partOfDay;
    }

    public void setPartOfDay(Enums.PartOfDay partOfDay) {
        this.partOfDay = partOfDay;
    }
}
