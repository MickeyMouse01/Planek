package com.example.thomas.plan.data.Models;

public class Settings {

    private boolean disableDeleteButton = false;

    public Settings(){

    }

    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }

    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }
}
