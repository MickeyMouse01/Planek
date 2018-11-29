package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;
import com.example.thomas.plan.Common.Enums;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Task implements Comparable<Task>{

    private final String uniqueID;
    private String name;
    private boolean passed;
    private String createdDate;
    private boolean isImageSet = false;
    private String idOfPlan;
    private Enums.PartOfDay partOfDay = Enums.PartOfDay.UNDEFINED;
    private String time = "";

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

    public boolean isImageSet() {
        return isImageSet;
    }

    public void setImageSet(boolean imageSet) {
        isImageSet = imageSet;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int compareTo(@NonNull Task task) {
        if (!this.getTime().isEmpty()){
            return timeInMillis(this.getTime()) - timeInMillis(task.getTime());
        } else {
            return Integer.compare(this.getPartOfDay().getCode(), task.getPartOfDay().getCode());
        }
    }

    private int timeInMillis(String time){
        DateFormat secondaryFormat = new SimpleDateFormat("H:mm");
        Date date = null;
        try {
            date = secondaryFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)date.getTime();
    }
}
