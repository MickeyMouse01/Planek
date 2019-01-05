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


}
