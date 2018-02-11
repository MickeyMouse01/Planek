package com.example.thomas.plan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * Created by pospe on 10.02.2018.
 */

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Client>> listOfClients;

    public MainViewModel(){

    }

    public LiveData<ArrayList<Client>> getListOfClients() {
        return listOfClients;
    }

}
