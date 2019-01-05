package com.example.thomas.plan;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.thomas.plan.viewmodels.MainViewModel;
import com.example.thomas.plan.viewmodels.AddEditClientViewModel;
import com.example.thomas.plan.viewmodels.AddEditPlanViewModel;
import com.example.thomas.plan.viewmodels.AddEditTaskViewModel;
import com.example.thomas.plan.data.Injection;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.viewmodels.ListOfDaysViewModel;
import com.example.thomas.plan.viewmodels.LoginViewModel;
import com.example.thomas.plan.viewmodels.NurseInfoViewModel;
import com.example.thomas.plan.viewmodels.NurseInfoEditViewModel;
import com.example.thomas.plan.viewmodels.PreviewFoodForClientViewModel;
import com.example.thomas.plan.viewmodels.PreviewPlanForClientViewModel;
import com.example.thomas.plan.viewmodels.ClientScreenViewModel;
import com.example.thomas.plan.viewmodels.PreviewSelectedTaskVieModel;
import com.example.thomas.plan.viewmodels.PreviewPlansViewModel;
import com.example.thomas.plan.viewmodels.ClientInfoViewModel;
import com.example.thomas.plan.viewmodels.PlanInfoViewModel;
import com.example.thomas.plan.viewmodels.PlanInfoEditViewModel;

/**
 * Created by Tomas on 14-Mar-18.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final Repository repository;

    private ViewModelFactory(Application app, Repository clients) {
        mApplication = app;
        repository = clients;
    }

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

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repository);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddEditClientViewModel.class)) {
            return (T) new AddEditClientViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddEditPlanViewModel.class)) {
            return (T) new AddEditPlanViewModel(repository);
        } else if (modelClass.isAssignableFrom(ClientInfoViewModel.class)) {
            return (T) new ClientInfoViewModel(repository);
        } else if (modelClass.isAssignableFrom(PlanInfoViewModel.class)) {
            return (T) new PlanInfoViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddEditTaskViewModel.class)) {
            return (T) new AddEditTaskViewModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewSelectedTaskVieModel.class)) {
            return (T) new PreviewSelectedTaskVieModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewPlanForClientViewModel.class)) {
            return (T) new PreviewPlanForClientViewModel(repository);
        } else if (modelClass.isAssignableFrom(NurseInfoViewModel.class)) {
            return (T) new NurseInfoViewModel(repository);
        } else if (modelClass.isAssignableFrom(NurseInfoEditViewModel.class)) {
            return (T) new NurseInfoEditViewModel(repository);
        } else if (modelClass.isAssignableFrom(ClientScreenViewModel.class)) {
            return (T) new ClientScreenViewModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewPlansViewModel.class)) {
            return (T) new PreviewPlansViewModel(repository);
        } else if(modelClass.isAssignableFrom(ListOfDaysViewModel.class)){
            return (T) new ListOfDaysViewModel(repository);
        } else if(modelClass.isAssignableFrom(PlanInfoEditViewModel.class)){
            return (T) new PlanInfoEditViewModel(repository);
        } else if(modelClass.isAssignableFrom(PreviewFoodForClientViewModel.class)){
            return (T) new PreviewFoodForClientViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

