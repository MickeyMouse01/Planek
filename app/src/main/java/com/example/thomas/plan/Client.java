package com.example.thomas.plan;

/**
 * Created by Thomas on 25.01.2018.
 */
import android.os.Parcel;
import android.os.Parcelable;

import com.example.thomas.plan.Common.Enums.TypeOfGroup;

import java.io.Serializable;


public class Client extends BasePerson  {


    private TypeOfGroup typeOfGroup;


    public Client(String name, String surname, TypeOfGroup typeOfGroup) {
        super(name, surname);
        this.typeOfGroup = typeOfGroup;
    }

    protected Client(Parcel in) {
        super(in);
        typeOfGroup = (TypeOfGroup) in.readValue(TypeOfGroup.class.getClassLoader());
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeValue(typeOfGroup);
    }

    public int describeContents() {
        return 0;
    }



    public void setTypeOfGroup(TypeOfGroup typeOfGroup) {
        this.typeOfGroup = typeOfGroup;
    }

    public TypeOfGroup getTypeOfGroup() {
        return typeOfGroup;
    }

}
