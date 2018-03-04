package com.example.thomas.plan.Common;

/**
 * Created by Thomas on 25.01.2018.
 */

public class Enums {

    public enum TypeOfGroup {
        GROUPA(0),
        GROUPB(1),
        GROUPC(2);

        private int code;

        TypeOfGroup(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Shift {
        MORNING,
        AFTERNOON,
        NIGHT
    }
}


