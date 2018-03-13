package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Remote.ClientsRemoteDataSource;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class ClientsRepository {

    public static ClientsRepository INSTANCE = null;

    private ClientsRemoteDataSource mTasksLocalDataSource;


    private ClientsRepository(@NonNull ClientsRemoteDataSource clients){
        mTasksLocalDataSource = clients;

    }

    public static ClientsRepository getInstance(ClientsRemoteDataSource clients){

        if (INSTANCE == null){
            INSTANCE = new ClientsRepository(clients);
        }
        return INSTANCE;
    }
}
