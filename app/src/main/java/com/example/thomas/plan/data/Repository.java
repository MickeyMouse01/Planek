package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
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
    public void getClient(@NonNull final String clientId, final LoadClientCallback callback){
        remoteDataSource.getClient(clientId, new LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                callback.onClientLoaded(client);
            }
        });
    }

    public void deleteClient(@NonNull String clientId){
        remoteDataSource.deleteClient(clientId);
    }

    public void savePlan(@NonNull Plan plan){
        remoteDataSource.savePlan(plan);
    }

    public void getPlans(@NonNull final LoadPlansCallback callback){
        remoteDataSource.getPlans(new LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                callback.onPlansLoaded(plans);
            }

        });
    }

    public void getPlan(@NonNull final String planId,final LoadPlanCallback callback){
        remoteDataSource.getPlan(planId, new LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                callback.onPlanLoaded(plan);
            }
        });
    }

    //todo bug, kdyz zbyde posledni plan/klienta, tak se odstrani z databaze ale ne z view
    public void deletePlan(@NonNull String planId){
        remoteDataSource.deletePlan(planId);
    }

}
