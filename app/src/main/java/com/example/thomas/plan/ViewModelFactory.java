package com.example.thomas.plan;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.thomas.plan.Clients.MainViewModel;
import com.example.thomas.plan.addEditClient.AddEditClientViewModel;
import com.example.thomas.plan.addEditPlan.AddEditPlanViewModel;
import com.example.thomas.plan.addEditTask.AddEditTaskViewModel;
import com.example.thomas.plan.data.Injection;
import com.example.thomas.plan.data.Repository;
import com.example.thomas.plan.listOfDays.ListOfDaysViewModel;
import com.example.thomas.plan.loginAndRegister.LoginViewModel;
import com.example.thomas.plan.nurseProfile.NurseProfileViewModel;
import com.example.thomas.plan.nurseProfileEdit.NurseProfileEditViewModel;
import com.example.thomas.plan.planForClient.PreviewClientViewModel;
import com.example.thomas.plan.previewTask.PreviewTaskViewModel;
import com.example.thomas.plan.selectedTask.SelectedTaskVieModel;
import com.example.thomas.plan.ui.previewplans.PreviewPlansViewModel;
import com.example.thomas.plan.viewClientInfo.ViewClientViewModel;
import com.example.thomas.plan.viewPlanInfo.ViewPlanInfoViewModel;

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
        } else if (modelClass.isAssignableFrom(ViewClientViewModel.class)) {
            return (T) new ViewClientViewModel(repository);
        } else if (modelClass.isAssignableFrom(ViewPlanInfoViewModel.class)) {
            return (T) new ViewPlanInfoViewModel(repository);
        } else if (modelClass.isAssignableFrom(AddEditTaskViewModel.class)) {
            return (T) new AddEditTaskViewModel(repository);
        } else if (modelClass.isAssignableFrom(SelectedTaskVieModel.class)) {
            return (T) new SelectedTaskVieModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewClientViewModel.class)) {
            return (T) new PreviewClientViewModel(repository);
        } else if (modelClass.isAssignableFrom(NurseProfileViewModel.class)) {
            return (T) new NurseProfileViewModel(repository);
        } else if (modelClass.isAssignableFrom(NurseProfileEditViewModel.class)) {
            return (T) new NurseProfileEditViewModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewTaskViewModel.class)) {
            return (T) new PreviewTaskViewModel(repository);
        } else if (modelClass.isAssignableFrom(PreviewPlansViewModel.class)) {
            return (T) new PreviewPlansViewModel(repository);
        } else if(modelClass.isAssignableFrom(ListOfDaysViewModel.class)){
            return (T) new ListOfDaysViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

