package com.example.thomas.plan.data.Models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Plan {

    private final String uniqueID;
    private String name;
    private String createdDate;
    private List<Task> listOfRelatesTasks = new ArrayList<>();
    //private Bitmap image;

    public Plan() {
        addToListOfRelatesTasks(new Task("name"));
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

    public List<Task> getListOfRelatesTasks() {
        return listOfRelatesTasks;
    }

    public void setListOfRelatesTasks(List<Task> listOfRelatesTasks) {
        this.listOfRelatesTasks = listOfRelatesTasks;
    }

    public void addToListOfRelatesTasks(Task task){
        if(listOfRelatesTasks == null){
            listOfRelatesTasks = new ArrayList<>();
        }
        listOfRelatesTasks.add(task);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }*/
}
