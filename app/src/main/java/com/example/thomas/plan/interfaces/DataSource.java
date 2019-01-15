package com.example.thomas.plan.interfaces;

import android.support.annotation.NonNull;

import com.example.thomas.plan.common.Enums;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Tomas on 31-Mar-18.
 */

public interface DataSource {

    interface SavedDataCallback {
        void onSavedData(@NonNull String message);
    }

    //nurse
    interface LoadNursesCallback {
        void onNursesLoaded(@NonNull List<Nurse> nurses);

    }

    interface LoadNurseCallback {
        void onNurseLoaded(Nurse nurse);
    }

    void searchNurseByEmail(@NonNull String email, LoadNurseCallback callback);

    void getNurses(@NonNull LoadNursesCallback callback);

    void getNurse(@NonNull String nurseId, LoadNurseCallback callback);

    void saveNurse(@NonNull Nurse nurse, SavedDataCallback callback);
    void deleteNurse(@NonNull String nurseId);



    //Clients
    interface LoadClientsCallback {
        void onClientsLoaded(@NonNull List<Client> clients);

    }

    interface LoadClientCallback {
        void onClientLoaded(Client client);
    }
    interface  LoadImageCallback {
        void onImageLoaded(byte[] bytes);
    }

    interface UploadImageCallback {
        void onImageUploaded();
    }

    interface GetPlansForDateCallBack {
        void onPlansForDateLoaded(HashMap<String, String> collection);
    }

    void getPlansForDate(@NonNull String clientId, GetPlansForDateCallBack callback);

    void uploadImage(@NonNull String name, byte[] data, UploadImageCallback callback);
    void downloadImage(@NonNull String name, LoadImageCallback callback);

    void getClients(@NonNull LoadClientsCallback callback);

    void getClient(@NonNull String clientId, LoadClientCallback callback);

    void saveClient(@NonNull Client client, SavedDataCallback callback);
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

    void savePlan(@NonNull Plan plan, SavedDataCallback callback);

    void deletePlan(@NonNull String planId);


    //Tasks
    interface LoadTasksCallback {
        void onTasksLoaded(@NonNull List<Task> tasks);
    }

    interface LoadTaskCallback {
        void onTaskLoaded(@NonNull Task task);
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTasksForPlan(@NonNull LoadTasksCallback callback, String planId);

    void getSpecificTasksForPlan(@NonNull LoadTasksCallback callback,
                                 String planId,
                                 Enums.PartOfDay partOfDay);

    void getTask(@NonNull String taskId, LoadTaskCallback callback);

    void saveTask(@NonNull Task task, String planId, SavedDataCallback callback);

    void deleteTask(@NonNull String taskId);

    void deleteTaskFromPlan(@NonNull String planId, @NonNull String taskId);


}
