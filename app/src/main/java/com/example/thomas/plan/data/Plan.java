package com.example.thomas.plan.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.support.annotation.NonNull;

/**
 * Created by Tomas on 05-Mar-18.
 */

//todo zkonvertovat Image na string

@Entity(tableName = "plan")
public class Plan {

    @NonNull
    @PrimaryKey()
    public String UniqueID;

    @ColumnInfo(name = "name")
    public String name;

   /* @ColumnInfo(name = "img")
    public Image img;*/

    @ColumnInfo(name = "isDone")
    public boolean isDone;

    public Plan() {
    }

    @NonNull
    public String getUniqueID() {
        return UniqueID;
    }

    public void setUniqueID(@NonNull String uniqueID) {
        UniqueID = uniqueID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }*/

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
