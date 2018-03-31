package com.example.thomas.plan.data;

import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Models.Client;

/**
 * Created by Tomas on 31-Mar-18.
 */

public interface DataSource {

    void getClients();

    void getClient(@NonNull String clinetId);

    void saveClient(@NonNull Client task);


}
