package com.example.thomas.plan.data.Models;

public class Settings {

    private boolean isDeleteInvisibleAfterPassed;
    private boolean isDeleteVisibleAllTime = false;

    public Settings() {

    }

    public boolean isDeleteVisibleAllTime() {
        return isDeleteVisibleAllTime;
    }

    public void setDeleteVisibleAllTime(boolean deleteVisibleAllTime) {
        isDeleteVisibleAllTime = deleteVisibleAllTime;
    }

    public boolean isDeleteInvisibleAfterPassed() {
        return isDeleteInvisibleAfterPassed;
    }

    public void setDeleteInvisibleAfterPassed(boolean disableDeleteButton) {
        this.isDeleteInvisibleAfterPassed = disableDeleteButton;
    }
}
