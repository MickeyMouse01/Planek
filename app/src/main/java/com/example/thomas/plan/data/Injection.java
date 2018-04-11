package com.example.thomas.plan.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.thomas.plan.data.Remote.RemoteDataSource;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class Injection {
    public static Repository provideClientsRepository(@NonNull Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance());
    }

}
