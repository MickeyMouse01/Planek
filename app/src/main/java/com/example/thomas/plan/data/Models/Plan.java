package com.example.thomas.plan.data.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by Tomas on 05-Mar-18.
 */

public class Plan {

    public final String uniqueID;
    public String name;
    public String pathToImg;
    public boolean isDone;

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

    public String getPathToImg() {
        return pathToImg;
    }

    public void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
