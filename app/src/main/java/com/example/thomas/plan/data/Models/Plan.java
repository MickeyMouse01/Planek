package com.example.thomas.plan.data.Models;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Plan implements Comparable<Plan>{

    private final String uniqueID;
    private String name;
    private String createdDate;
    private Map<String, Task> listOfRelatesTasks;
    private boolean imageSet = false;

    public Plan() {
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

    public void setListOfRelatesTasks(Map<String, Task> listOfRelatesTasks) {
        this.listOfRelatesTasks = listOfRelatesTasks;
    }

    public Map<String, Task> getListOfRelatesTasks() {
        return listOfRelatesTasks;
    }

    public void addToListOfRelatesTasks(Task task){
        if(listOfRelatesTasks == null){
            listOfRelatesTasks = new HashMap<>();
        }
        listOfRelatesTasks.put(task.getUniqueID(), task);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImageSet() {
        return imageSet;
    }

    public void setImageSet(boolean imageSet) {
        this.imageSet = imageSet;
    }

    @Override
    public int compareTo(@NonNull Plan o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
