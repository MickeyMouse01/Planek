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

    public enum PartOfDay {
        MORNING(0),
        LUNCH(1),
        AFTERNOON(2),
        DINNER(3),
        NIGHT(4),
        UNDEFINED(5);

        private int code;
        PartOfDay(int code) {
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

        public String getPartOfDay(){
            switch (getCode()) {
                case 0:
                    return "Dopoledne";
                case 1:
                    return "Oběd";
                case 2:
                    return "Odpoledne";
                case 3:
                    return "Večeře";
                case 4:
                    return "V noci";
                default:
                    return "Nevybráno";
            }
        }
    }

    public enum Week {
        WEEK1(1),
        WEEK2(2),
        WEEK3(3),
        WEEK4(4),
        WEEK5(5),
        WEEK6(6),
        WEEK7(7),
        WEEK8(8);

        private int code;

        Week(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            switch (getCode()) {
                case 1:
                    return "Týden 1";
                case 2:
                    return "Týden 2";
                case 3:
                    return "Týden 3";
                case 4:
                    return "Týden 4";
                case 5:
                    return "Týden 5";
                case 6:
                    return "Týden 6";
                case 7:
                    return "Týden 7";
                case 8:
                    return "Týden 8";
                default:
                    return "Nevybráno";
            }
        }
    }

    public enum Day {
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);

        private int code;
        Day(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            switch (getCode()) {
                case 0:
                    return "Pondělí";
                case 1:
                    return "Úterý";
                case 2:
                    return "Středa";
                case 3:
                    return "Čtvrtek";
                case 4:
                    return "Pátek";
                case 5:
                    return "Sobota";
                case 6:
                    return "Neděle";
                default:
                    return "Nevybráno";
            }
        }

        public String getNameOfDay() {
            switch (getCode()) {
                case 0:
                    return "Pondělí";
                case 1:
                    return "Úterý";
                case 2:
                    return "Středa";
                case 3:
                    return "Čtvrtek";
                case 4:
                    return "Pátek";
                case 5:
                    return "Sobota";
                case 6:
                    return "Neděle";
                default:
                    return "Nevybráno";
            }
        }
    }
}


