package com.example.thomas.plan.data.Models;

public class Settings {

    private boolean disableDeleteButton;

    public Settings() {

    }

    public boolean isDisabledDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisabledDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }
}
