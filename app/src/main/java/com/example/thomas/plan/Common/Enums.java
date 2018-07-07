package com.example.thomas.plan.Common;

/**
 * Created by Thomas on 25.01.2018.
 */

public class Enums {

    public enum TypeOfGroup {
        GROUPA(0),
        GROUPB(1),
        GROUPC(2),
        UNDEFINED(3);

        private int code;
        TypeOfGroup(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public String getNameOfGroup() {
            switch (getCode()) {
                case 0:
                    return "Skupina A";
                case 1:
                    return "Skupina B";
                case 2:
                    return "Skupina C";
                default:
                    return "Skupina nevybrána";
            }
        }
    }

    public enum ActivityNavigator {
        CLIENTS_FRAGMENT(0),
        PLANS_FRAGMENT(1),
        PREVIEW_CLIENT(2);

        private int code;
        ActivityNavigator(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }

    public enum Shift {
        MORNING(0),
        AFTERNOON(1),
        NIGHT(2),
        UNDEFINED(3);

        private int code;
        Shift(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public String getNameOfShift() {
            switch (getCode()) {
                case 0:
                    return "Ranní služba";
                case 1:
                    return "Odpolední služba";
                case 2:
                    return "Noční služba";
                default:
                    return "Služba nevybrána";
            }
        }
    }
}


