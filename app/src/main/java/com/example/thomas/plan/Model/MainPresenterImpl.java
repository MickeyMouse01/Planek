package com.example.thomas.plan.Model;

import com.example.thomas.plan.Client;
import com.example.thomas.plan.Presenter.MainPresenter;
import com.example.thomas.plan.View.MainView;

/**
 * Created by Thomas on 25.01.2018.
 */

public class MainPresenterImpl implements MainPresenter {

    MainView mMainView;

    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;
    }
    @Override
    public void performAddClient(Client client) {
        if (client != null) {
            mMainView.addClient(client);
        }

    }
}
