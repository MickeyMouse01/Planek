package com.example.thomas.plan.data.Models;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Plan {

    private final String uniqueID;
    private String name;
    //private Bitmap image;

    public Plan() {
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

    /*public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }*/
}
