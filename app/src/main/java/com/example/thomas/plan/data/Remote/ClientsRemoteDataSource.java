package com.example.thomas.plan.data.Remote;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class ClientsRemoteDataSource {

    public static ClientsRemoteDataSource INSTANCE = null;

    private DatabaseReference mDatabase;



    private ClientsRemoteDataSource(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static ClientsRemoteDataSource getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ClientsRemoteDataSource();
        }

        return INSTANCE;
    }
    public Boolean saveClient(@NonNull Client client){

        mDatabase.child("client").child("UserID").setValue(client);
        return true;
    }




}
