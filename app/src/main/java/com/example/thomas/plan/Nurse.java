package com.example.thomas.plan;

/**
 * Created by Thomas on 25.01.2018.
 */

public class Nurse extends BasePerson {

    private int shift;

    public Nurse(String name, String surname, int shift) {
        super(name, surname);
        this.shift = shift;

    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

}
