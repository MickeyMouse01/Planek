package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Remote.RemoteDataSource;

import java.util.List;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class ClientsRepository implements DataSource {

    private static volatile ClientsRepository INSTANCE;

    private RemoteDataSource remoteDataSource;

    private ClientsRepository(RemoteDataSource remoteDataSource){
        this.remoteDataSource = remoteDataSource;
    }

    public static ClientsRepository getInstance(RemoteDataSource remoteDataSource){
        if (INSTANCE == null) {
            synchronized (ClientsRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new ClientsRepository(remoteDataSource);
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

}
