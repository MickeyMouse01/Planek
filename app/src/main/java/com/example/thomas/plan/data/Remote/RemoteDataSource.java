package com.example.thomas.plan.data.Remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.data.Models.Plan;
import com.example.thomas.plan.data.Models.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private StorageReference mStorage;
    private FirebaseAuth mAuth;


    // Prevent direct instantiation.
    private RemoteDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }



    @Override
    public void searchNurseByEmail(@NonNull String email, final LoadNurseCallback callback) {
        Query query = mDatabase.child("nurses").orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Nurse>> t = new GenericTypeIndicator<Map<String, Nurse>>() {
                };
                Map<String, Nurse> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Nurse> nurses = new ArrayList<>(map.values());
                    Nurse nurse = nurses.get(0);
                    callback.onNurseLoaded(nurse);
                } else {
                    callback.onNurseLoaded(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getNurses(@NonNull final LoadNursesCallback callback) {
        mDatabase.getDatabase().getReference("nurses")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Nurse>> t = new GenericTypeIndicator<Map<String, Nurse>>() {
                };
                Map<String, Nurse> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Nurse> nurses = new ArrayList<>(map.values());
                    callback.onNursesLoaded(nurses);
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
    public void getNurse(@NonNull final String nurseId, final LoadNurseCallback callback) {
        mDatabase.getDatabase().getReference("nurses")
                .getRef().addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Nurse nurse = dataSnapshot.child(nurseId).getValue(Nurse.class);
                callback.onNurseLoaded(nurse);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(ON_CANCELLED, "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void saveNurse(@NonNull Nurse nurse) {
        mDatabase.child("nurses")
                .child(nurse.getUniqueID()).setValue(nurse);
    }

    @Override
    public void deleteNurse(@NonNull String nurseId) {
        mDatabase.child("nurses").child(nurseId).removeValue();
    }

    @Override
    public void uploadImage(@NonNull String name, byte[] data, final UploadImageCallback callback) {
        UploadTask uploadTask = mStorage.child(name).putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onImageUploaded();
            }
        });
    }

    public void downloadImage(@NonNull String name, final LoadImageCallback callback) {
        StorageReference image = mStorage.child(name);
        final long SIZE = 256 * 256;
        image.getBytes(SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                callback.onImageLoaded(bytes);
            }
        });
    }


    //Clients
    @Override
    public void getClients(@NonNull final LoadClientsCallback callback) {
        mDatabase.getDatabase().getReference("clients").orderByChild("createdDate")
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
    public void saveClient(@NonNull Client client,final SavedDataCallback callback) {
        mDatabase.child("clients")
                .child(client.getUniqueID()).setValue(client, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    callback.onSavedData("Data nemohla být uložena " + databaseError.getMessage());
                } else {
                    callback.onSavedData("Klient byl úspěšně uložen");
                }
            }
        });
    }

    @Override
    public void deleteClient(@NonNull String clientId) {
        mDatabase.child("clients").child(clientId).removeValue();
    }

    @Override
    public void searchClientByUsername(@NonNull String username, final LoadClientCallback callback) {
        Query query = mDatabase.child("clients").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Client>> t = new GenericTypeIndicator<Map<String, Client>>() {
                };
                Map<String, Client> map = dataSnapshot.getValue(t);
                if (map != null) {
                    List<Client> clients = new ArrayList<>(map.values());
                    Client client = clients.get(0);
                    callback.onClientLoaded(client);
                } else {
                    callback.onClientLoaded(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Problem", databaseError.toString());
            }
        });
    }

    //Plans
    public void getPlans(@NonNull final LoadPlansCallback callback) {

        mDatabase.getDatabase().getReference("plans")
                .getRef().addValueEventListener(new ValueEventListener() {
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
                GenericTypeIndicator<Map<String, Task>> t = new GenericTypeIndicator<Map<String, Task>>() {
                };
                Map<String, Task> map = dataSnapshot.child(planId)
                        .child(LIST_OF_RELATES_TASKS).getValue(t);

                if (map != null) {
                    List<Task> tasks = new ArrayList<>(map.values());
                    callback.onTasksLoaded(tasks);
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
    public void saveTask(@NonNull Task task, String planId, final SavedDataCallback callback) {
        mDatabase.child("plans").child(planId).child(LIST_OF_RELATES_TASKS)
                .child(task.getUniqueID()).setValue(task, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    callback.onSavedData("Data nemohla být uložena " + databaseError.getMessage());
                } else {
                    callback.onSavedData("Aktivita byla úspěšně uložena.");
                }
            }
        });
    }

    public void deleteTask(@NonNull String taskId) {
        mDatabase.child("tasks").child(taskId).removeValue();
    }

    @Override
    public void deleteTaskFromPlan(@NonNull String planId, @NonNull String taskId) {
        mDatabase.child("plans").child(planId)
                .child(LIST_OF_RELATES_TASKS).child(taskId).removeValue();
    }


}
