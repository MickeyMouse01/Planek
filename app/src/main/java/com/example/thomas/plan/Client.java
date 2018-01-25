package com.example.thomas.plan;

/**
 * Created by Thomas on 25.01.2018.
 */
import com.example.thomas.plan.Common.Enums.TypeOfGroup;


public class Client extends BasePerson {

    private TypeOfGroup typeOfGroup;


    public Client(String name, String surname, TypeOfGroup typeOfGroup) {
        super(name, surname);
        this.typeOfGroup = typeOfGroup;
    }

    public void setTypeOfGroup(TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public TypeOfGroup getTypeOfGroup() {
        return typeOfGroup;
    }


}
