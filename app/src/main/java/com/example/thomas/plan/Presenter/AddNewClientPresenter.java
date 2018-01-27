package com.example.thomas.plan.Presenter;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;

/**
 * Created by Thomas on 27.01.2018.
 */

public interface AddNewClientPresenter {

    void performAddNewClient(String firstName, String surname, TypeOfGroup typeOfGroup);
}
