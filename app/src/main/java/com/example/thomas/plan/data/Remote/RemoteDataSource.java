package com.example.thomas.plan.data.Remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.data.DataSource;
import com.example.thomas.plan.data.Models.Client;
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

    @Override
    public void getClients(@NonNull final LoadClientsCallback callback) {

        mDatabase.getDatabase().getReference("clients")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<Map<String, Client>> t = new GenericTypeIndicator<Map<String, Client>>() {
                };
                Map<String, Client> map = dataSnapshot.getValue(t);
                List<Client> clients = new ArrayList<>(map.values());
                callback.onClientsLoaded(clients);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("blah", "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void getClient(@NonNull final String clientId) {
        mDatabase.getDatabase().getReference("clients")
                .getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Client client = dataSnapshot.child(clientId).getValue(Client.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("blah", "Failed to read app title value.", error.toException());
            }
        });
    }

    @Override
    public void saveClient(@NonNull Client client) {
        mDatabase.child("clients")
                .child(client.getUniqueID()).setValue(client);
    }
}
