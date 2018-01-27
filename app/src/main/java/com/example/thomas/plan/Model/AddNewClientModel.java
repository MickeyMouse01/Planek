package com.example.thomas.plan.Model;

import android.text.TextUtils;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;
import com.example.thomas.plan.Presenter.AddNewClientPresenter;
import com.example.thomas.plan.View.AddNewClientView;

/**
 * Created by Thomas on 27.01.2018.
 */

public class AddNewClientModel implements AddNewClientPresenter {

    AddNewClientView addNewClientView;

    public AddNewClientModel(AddNewClientView addNewClientView) {
        this.addNewClientView = addNewClientView;
    }


    @Override
    public void performAddNewClient(String firstName, String surname, TypeOfGroup typeOfGroup) {

        addNewClientView.addSuccess( firstName,  surname,  typeOfGroup);
    }
}
