package com.example.thomas.plan.View;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;

/**
 * Created by Thomas on 27.01.2018.
 */

public interface AddNewClientView {

    void addSuccess(String firsName, String surname, TypeOfGroup typeOfGroup);
    void addError();
    void addValidations();
}
