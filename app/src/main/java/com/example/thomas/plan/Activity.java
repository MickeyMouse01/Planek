package com.example.thomas.plan;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.Time;

import java.util.Calendar;

/**
 * Created by Thomas on 28.01.2018.
 */

public class Activity implements Parcelable {

    //todo passing Activity between activities
    //todo also atribute time type of Calendar
    //b;ah


    private String name;
    private Image image;
    private boolean isDone;
    private Calendar time;

    public Activity(String name, Image image, boolean isDone,Calendar time) {
        this.name = name;
        this.image = image;
        this.isDone = isDone;
        this.time = time;
    }

    protected Activity(Parcel in) {
        name = in.readString();
        isDone = in.readByte() != 0;
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (isDone ? 1 : 0));
        parcel.writeLong(time.getTimeInMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }



}
