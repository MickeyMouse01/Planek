package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;

import java.util.List;

/**
 * Created by Tomas on 31-Mar-18.
 */

public interface DataSource {

    //Clients
    interface LoadClientsCallback {
        void onClientsLoaded(@NonNull List<Client> clients);

    }

    interface LoadClientCallback {
        void onClientLoaded(Client client);
    }
    interface  LoadImageCallback{
        void onImageLoaded(byte[] bytes);
    }

    void uploadImage(@NonNull String name, byte[] data);
    void downloadImage(@NonNull String name, final LoadImageCallback callback);

    void getClients(@NonNull LoadClientsCallback callback);

    void getClient(@NonNull String clientId, LoadClientCallback callback);

    void saveClient(@NonNull Client client);
    void deleteClient(@NonNull String clientId);

    void searchClientByUsername(@NonNull String username, LoadClientCallback callback);

    //Plans
    interface LoadPlansCallback {
        void onPlansLoaded(@NonNull List<Plan> plans);
    }

    interface LoadPlanCallback {
        void onPlanLoaded(@NonNull Plan plan);
    }

    void getPlans(@NonNull LoadPlansCallback callback);

    void getPlan(@NonNull String planId, LoadPlanCallback callback);

    void savePlan(@NonNull Plan task);

    void deletePlan(@NonNull String planId);


    //Tasks
    interface LoadTasksCallback {
        void onTasksLoaded(@NonNull List<Task> tasks);
    }

    interface LoadTaskCallback {
        void onTaskLoaded(@NonNull Task task);
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getSpecificTasksForPlan(@NonNull LoadTasksCallback callback, String planId);

    void getTask(@NonNull String taskId, LoadTaskCallback callback);

    void saveTask(@NonNull Task task, String planId);

    void deleteTask(@NonNull String taskId);

    void deleteTaskFromPlan(@NonNull String planId, @NonNull String taskId);


}
