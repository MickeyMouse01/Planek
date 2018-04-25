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

    void getClients(@NonNull LoadClientsCallback callback);

    void getClient(@NonNull String clinetId);

    void saveClient(@NonNull Client task);

    interface LoadPlansCallback {
        void onPlansLoaded(@NonNull List<Plan> plans);
    }

    void getPlans(@NonNull LoadPlansCallback callback);

    void getPlan(@NonNull String clinetId);

    void savePlan(@NonNull Plan task);


}
