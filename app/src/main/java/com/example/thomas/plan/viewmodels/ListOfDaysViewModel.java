package com.example.thomas.plan.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.Repository;

import java.util.Arrays;
import java.util.List;

public class ListOfDaysViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<List<String>> listOfDays;
    private MutableLiveData<List<String>> listOfWeeks;

    public ListOfDaysViewModel(Repository repository){
        this.repository = repository;
    }

    public MutableLiveData<List<String>> getListOfDays(){
        if(listOfDays == null){
            listOfDays = new MutableLiveData<>();
            loadDays();
        }
        return listOfDays;
    }

    void loadDays(){
        List<String> days = Arrays.asList(
                Enums.Day.MONDAY.getNameOfDay(),
                Enums.Day.TUESDAY.getNameOfDay(),
                Enums.Day.WEDNESDAY.getNameOfDay(),
                Enums.Day.THURSDAY.getNameOfDay(),
                Enums.Day.FRIDAY.getNameOfDay(),
                Enums.Day.SATURDAY.getNameOfDay(),
                Enums.Day.SUNDAY.getNameOfDay()
        );
        listOfDays.setValue(days);
    }

    public MutableLiveData<List<String>> getListOfWeeks(){
        if(listOfWeeks == null){
            listOfWeeks = new MutableLiveData<>();
            loadWeeks();
        }
        return listOfWeeks;
    }

    private void loadWeeks(){
        List<String> days = Arrays.asList(
                Enums.Week.WEEK1.toString(),
                Enums.Week.WEEK2.toString(),
                Enums.Week.WEEK3.toString(),
                Enums.Week.WEEK4.toString(),
                Enums.Week.WEEK5.toString(),
                Enums.Week.WEEK6.toString(),
                Enums.Week.WEEK7.toString(),
                Enums.Week.WEEK8.toString()
        );
        listOfWeeks.setValue(days);
    }


}
