package com.example.thomas.plan.viewClient;

import android.arch.lifecycle.ViewModel;

import com.example.thomas.plan.data.Repository;

/**
 * Created by Tomas on 18-Apr-18.
 */

public class ViewClientViewModel extends ViewModel {

    private Repository repository;

    public ViewClientViewModel(Repository mRepository) {
        this.repository = mRepository;
    }
}
