package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class ClientsRepository {

    private static volatile ClientsRepository INSTANCE;
    private DatabaseReference mDatabase;

    private ClientsRepository(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        mDatabase.child("client").child("UserID").setValue(client);
        return true;
    }
}
