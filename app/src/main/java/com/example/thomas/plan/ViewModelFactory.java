package com.example.thomas.plan;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.thomas.plan.Clients.ClientsViewModel;
import com.example.thomas.plan.addEditClient.AddEditClientViewModel;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.data.Injection;
import com.example.thomas.plan.viewClient.ViewClientViewModel;

/**
 * Created by Tomas on 14-Mar-18.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final Repository repository;

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

    private ViewModelFactory(Application app, Repository clients) {
        mApplication = app;
        repository = clients;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientsViewModel.class)) {
            //noinspection unchecked
            return (T) new ClientsViewModel(repository);
        } else if(modelClass.isAssignableFrom(AddEditClientViewModel.class)) {
            return (T) new AddEditClientViewModel(repository);
        }
        else if(modelClass.isAssignableFrom(ViewClientViewModel.class)) {
            return (T) new ViewClientViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

