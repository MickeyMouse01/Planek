package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;

import java.util.List;

/**
 * Created by Tomas on 31-Mar-18.
 */

public interface DataSource {

    interface LoadClientsCallback {
        void onClientsLoaded(@NonNull List<Client> clients);

    }
    interface LoadClientCallback {
        void onClientLoaded(@NonNull Client client);
    }

    void getClients(@NonNull LoadClientsCallback callback);

    void getClient(@NonNull String clientId, LoadClientCallback callback);

    void saveClient(@NonNull Client client);

    interface LoadPlansCallback {
        void onPlansLoaded(@NonNull List<Plan> plans);
    }
    interface LoadPlanCallback {
        void onPlanLoaded(@NonNull Plan plan);
    }

    void getPlans(@NonNull LoadPlansCallback callback);

    void getPlan(@NonNull String planId, LoadPlanCallback callback);

    void savePlan(@NonNull Plan task);


}
