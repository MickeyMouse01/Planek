package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.example.thomas.plan.data.Remote.RemoteDataSource;
import com.example.thomas.plan.interfaces.DataSource;

import java.util.List;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class Repository implements DataSource {

    private static volatile Repository INSTANCE;
    private RemoteDataSource remoteDataSource;

    private Repository(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static Repository getInstance(RemoteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(remoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void searchNurseByEmail(@NonNull String email, final LoadNurseCallback callback) {
        remoteDataSource.searchNurseByEmail(email, new LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                callback.onNurseLoaded(nurse);
            }
        });
    }

    @Override
    public void getNurses(@NonNull final LoadNursesCallback callback) {
        remoteDataSource.getNurses(new LoadNursesCallback() {
            @Override
            public void onNursesLoaded(@NonNull List<Nurse> nurses) {
                callback.onNursesLoaded(nurses);
            }
        });
    }

    @Override
    public void getNurse(@NonNull String nurseId, final LoadNurseCallback callback) {
        remoteDataSource.getNurse(nurseId, new LoadNurseCallback() {
            @Override
            public void onNurseLoaded(Nurse nurse) {
                callback.onNurseLoaded(nurse);
            }
        });
    }

    @Override
    public void saveNurse(@NonNull Nurse nurse, SavedDataCallback callback) {
        remoteDataSource.saveNurse(nurse, callback);
    }

    @Override
    public void deleteNurse(@NonNull String nurseId) {
        remoteDataSource.deleteNurse(nurseId);
    }

    @Override
    public void uploadImage(@NonNull String name, byte[] data, final UploadImageCallback callback) {
        remoteDataSource.uploadImage(name, data, new UploadImageCallback() {
            @Override
            public void onImageUploaded() {
                callback.onImageUploaded();
            }
        });
    }

    @Override
    public void downloadImage(@NonNull String name, final LoadImageCallback callback) {
        remoteDataSource.downloadImage(name, new LoadImageCallback() {
            @Override
            public void onImageLoaded(byte[] bytes) {
                callback.onImageLoaded(bytes);
            }
        });
    }

    //Clients
    public void saveClient(@NonNull Client client, SavedDataCallback callback) {
        remoteDataSource.saveClient(client, callback);
    }

    public void getClients(@NonNull final LoadClientsCallback callback) {
        remoteDataSource.getClients(new LoadClientsCallback() {
            @Override
            public void onClientsLoaded(@NonNull List<Client> clients) {
                callback.onClientsLoaded(clients);
            }

        });
    }

    public void getClient(@NonNull final String clientId, final LoadClientCallback callback) {
        remoteDataSource.getClient(clientId, new LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                callback.onClientLoaded(client);
            }
        });
    }

    public void deleteClient(@NonNull String clientId) {
        remoteDataSource.deleteClient(clientId);
    }

    @Override
    public void searchClientByUsername(@NonNull String username, final LoadClientCallback callback) {
        remoteDataSource.searchClientByUsername(username, new LoadClientCallback() {
            @Override
            public void onClientLoaded(@NonNull Client client) {
                callback.onClientLoaded(client);
            }
        });
    }

    //Plans
    public void savePlan(@NonNull Plan plan, SavedDataCallback callback) {
        remoteDataSource.savePlan(plan, callback);
    }

    public void getPlans(@NonNull final LoadPlansCallback callback) {
        remoteDataSource.getPlans(new LoadPlansCallback() {
            @Override
            public void onPlansLoaded(@NonNull List<Plan> plans) {
                callback.onPlansLoaded(plans);
            }

        });
    }

    public void getPlan(@NonNull final String planId, final LoadPlanCallback callback) {
        remoteDataSource.getPlan(planId, new LoadPlanCallback() {
            @Override
            public void onPlanLoaded(@NonNull Plan plan) {
                callback.onPlanLoaded(plan);
            }
        });
    }

    //todo bug, kdyz zbyde posledni plan/klienta, tak se odstrani z databaze ale ne z view
    public void deletePlan(@NonNull String planId) {
        remoteDataSource.deletePlan(planId);
    }

    //Tasks
    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        remoteDataSource.getTasks(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                callback.onTasksLoaded(tasks);
            }

        });
    }

    @Override
    public void getTasksForPlan(@NonNull final LoadTasksCallback callback, String planId) {
        remoteDataSource.getTasksForPlan(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                callback.onTasksLoaded(tasks);
            }
        }, planId);
    }

    @Override
    public void getSpecificTasksForPlan(@NonNull final LoadTasksCallback callback, String planId, Enums.PartOfDay partOfDay) {
        remoteDataSource.getSpecificTasksForPlan(new LoadTasksCallback() {
            @Override
            public void onTasksLoaded(@NonNull List<Task> tasks) {
                callback.onTasksLoaded(tasks);
            }
        }, planId , partOfDay);
    }


    @Override
    public void getTask(@NonNull final String taskId, final LoadTaskCallback callback) {
        remoteDataSource.getTask(taskId, new LoadTaskCallback() {
            @Override
            public void onTaskLoaded(@NonNull Task task) {
                callback.onTaskLoaded(task);
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task, String planId, SavedDataCallback callback) {
        remoteDataSource.saveTask(task, planId, callback);
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        remoteDataSource.deleteTask(taskId);
    }

    @Override
    public void deleteTaskFromPlan(@NonNull String planId, @NonNull String taskId) {
        remoteDataSource.deleteTaskFromPlan(planId, taskId);
    }
}
