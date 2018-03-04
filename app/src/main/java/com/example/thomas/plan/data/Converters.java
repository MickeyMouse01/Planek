package com.example.thomas.plan.data;

import android.arch.persistence.room.TypeConverter;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.Common.Enums.TypeOfGroup;

/**
 * Created by Tomas on 04-Mar-18.
 */

public  class Converters {

    public static class TypeOfGroupConverter {

        @TypeConverter
        public static TypeOfGroup toStatus(int status) {
            if (status == TypeOfGroup.GROUPA.getCode()) {
                return TypeOfGroup.GROUPA;
            } else if (status == TypeOfGroup.GROUPB.getCode()) {
                return TypeOfGroup.GROUPB;
            } else if (status == TypeOfGroup.GROUPC.getCode()) {
                return TypeOfGroup.GROUPC;
            } else {
                throw new IllegalArgumentException("Could not recognize status");
            }
        }

        @TypeConverter
        public static int toInteger(TypeOfGroup status) {
            int blah = 3;
            return status.getCode();
        }
    }
}
