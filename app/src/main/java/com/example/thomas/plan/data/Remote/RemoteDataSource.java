package com.example.thomas.plan.data.Remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Tomas on 31-Mar-18.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    private final String NAME_OF_CLASS = getClass().getName();
    private final String ON_CANCELLED = "RemoteDataSource";
    private final String LIST_OF_RELATES_TASKS = "listOfRelatesTasks";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    // Prevent direct instantiation.
    private RemoteDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    //Clients
    @Override
    public void getClients(@NonNull final LoadClientsCallback callback) {

        mDatabase.getDatabase().getReference("clients")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Client>> t = new GenericTypeIndicator<Map<String, Client>>() {
                };
                Map<String, Client> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Client> clients = new ArrayList<>(map.values());
                    callback.onClientsLoaded(clients);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void getClient(@NonNull final String clientId, final LoadClientCallback callback) {
        mDatabase.getDatabase().getReference("clients")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Client client = dataSnapshot.child(clientId).getValue(Client.class);
                callback.onClientLoaded(client);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void saveClient(@NonNull Client client) {
        mDatabase.child("clients")
                .child(client.getUniqueID()).setValue(client);
    }

    @Override
    public void deleteClient(@NonNull String clientId) {
        mDatabase.child("clients").child(clientId).removeValue();
    }

    //Plans
    public void getPlans(@NonNull final LoadPlansCallback callback) {

        mDatabase.getDatabase().getReference("plans")
                .getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Plan>> t = new GenericTypeIndicator<Map<String, Plan>>() {
                };
                Map<String, Plan> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Plan> plans = new ArrayList<>(map.values());
                    callback.onPlansLoaded(plans);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void getPlan(@NonNull final String planId, final LoadPlanCallback callback) {
        mDatabase.getDatabase().getReference("plans")
                .getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Plan plan = dataSnapshot.child(planId).getValue(Plan.class);
                callback.onPlanLoaded(plan);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void savePlan(@NonNull Plan plan) {
        mDatabase.child("plans")
                .child(plan.getUniqueID()).setValue(plan);
    }

    /*private DatabaseReference getSpecificPlanReference(String planId) {
        return mDatabase.child("plans").child(planId);
    }*/

    public void deletePlan(@NonNull String planId) {
        mDatabase.child("plans").child(planId).removeValue();
    }

    //Tasks
    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        mDatabase.getDatabase().getReference("tasks")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Task>> t = new GenericTypeIndicator<Map<String, Task>>() {
                };
                Map<String, Task> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Task> tasks = new ArrayList<>(map.values());
                    callback.onTasksLoaded(tasks);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void getSpecificTasksForPlan(@NonNull final LoadTasksCallback callback, final String planId) {

        mDatabase.getDatabase().getReference("plans")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                GenericTypeIndicator<ArrayList<Task>> t = new GenericTypeIndicator<ArrayList<Task>>() {
                };
                ArrayList<Task> map = dataSnapshot
                        .child(planId).child(LIST_OF_RELATES_TASKS)
                        .getValue(t);
                if (map != null) {

                    callback.onTasksLoaded(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getTask(@NonNull final String taskId, final LoadTaskCallback callback) {
        mDatabase.getDatabase().getReference("tasks")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.child(taskId).getValue(Task.class);
                callback.onTaskLoaded(task);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void saveTask(@NonNull Task task, String planId) {
        mDatabase.child("tasks")
                .child(task.getUniqueID()).setValue(task);
    }

    public void deleteTask(@NonNull String taskId) {
        mDatabase.child("tasks").child(taskId).removeValue();
    }

    @Override
    public void deleteTaskFromPlan(@NonNull String planId, @NonNull Integer position) {
        mDatabase.child("plans").child(planId)
                .child(LIST_OF_RELATES_TASKS)
                .child(position.toString()).removeValue();
    }
}
