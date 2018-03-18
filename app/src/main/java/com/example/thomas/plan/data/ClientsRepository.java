package com.example.thomas.plan.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.data.Models.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class ClientsRepository {

    private static volatile ClientsRepository INSTANCE;
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    private ClientsRepository(){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static ClientsRepository getInstance(){
        if (INSTANCE == null) {
            synchronized (ClientsRepository.class){
                if (INSTANCE == null) {
                    INSTANCE = new ClientsRepository();
                }
            }
        }
        return INSTANCE;
    }
    public Boolean saveClient(@NonNull Client client){
        mDatabase.child("clients")
                .child(client.getUniqueID()).setValue(client);
        return true;
    }
    public void getClient(@NonNull final String clientId){

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
}
