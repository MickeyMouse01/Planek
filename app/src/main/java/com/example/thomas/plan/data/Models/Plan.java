package com.example.thomas.plan.data.Models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Plan {

    private final String uniqueID;
    private String name;
    private String createdDate;
    private Map<String, Task> listOfRelatesTasks;
    private boolean hasImage = false;


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
            listOfRelatesTasks = new HashMap<>();;
        }
        listOfRelatesTasks.put(task.getUniqueID(), task);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
}
