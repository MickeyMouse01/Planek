package com.example.thomas.plan;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.example.thomas.plan.data.ClientsRepository;
import com.example.thomas.plan.data.Injection;

/**
 * Created by Tomas on 14-Mar-18.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory INSTANCE;


    private final Application mApplication;

    private final ClientsRepository mTasksRepository;

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideClientsRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application app, ClientsRepository clients){
        mApplication = app;
        mTasksRepository = clients;
    }
}
