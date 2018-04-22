package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Remote.RemoteDataSource;

import java.util.List;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class Repository implements DataSource {

    private static volatile Repository INSTANCE;

    private RemoteDataSource remoteDataSource;

    private Repository(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(RemoteDataSource remoteDataSource){
        if (INSTANCE == null) {
            synchronized (Repository.class){
                if (INSTANCE == null) {
                    INSTANCE = new Repository(remoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public void saveClient(@NonNull Client client){
       remoteDataSource.saveClient(client);
    }
    public void getClients(@NonNull final LoadClientsCallback callback){
        remoteDataSource.getClients(new LoadClientsCallback() {
            @Override
            public void onClientsLoaded(@NonNull List<Client> clients) {
                callback.onClientsLoaded(clients);
            }

        });
    }

    public void getClient(@NonNull final String clientId){
        remoteDataSource.getClient(clientId);
    }

    public void deleteClient(@NonNull String clientId){
        remoteDataSource.deleteClient(clientId);
    }

}
