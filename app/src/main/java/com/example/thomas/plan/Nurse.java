package com.example.thomas.plan;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 25.01.2018.
 */

public class Nurse extends BasePerson implements Parcelable {

    //TODO extend this class with parcelable
    //TODO add atribute, list of clients


    private int shift;

    public Nurse(String name, String surname, int shift) {
        super(name, surname);
        this.shift = shift;

    }

    protected Nurse(Parcel in) {
        super(in);
        shift = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(shift);
    }

    public static final Creator<Nurse> CREATOR = new Creator<Nurse>() {
        @Override
        public Nurse createFromParcel(Parcel in) {
            return new Nurse(in);
        }

        @Override
        public Nurse[] newArray(int size) {
            return new Nurse[size];
        }
    };

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
