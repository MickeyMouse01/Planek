package com.example.thomas.plan.data.Models;

public class Settings {

    private boolean isDeleteInvisibleAfterPassed;


    public Settings() {

    }



    public boolean isDeleteInvisibleAfterPassed() {
        return isDeleteInvisibleAfterPassed;
    }

    public void setDeleteInvisibleAfterPassed(boolean disableDeleteButton) {
        this.isDeleteInvisibleAfterPassed = disableDeleteButton;
    }
}
