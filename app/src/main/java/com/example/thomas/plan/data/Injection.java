package com.example.thomas.plan.data;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Tomas on 13-Mar-18.
 */

public class Injection {
    public static ClientsRepository provideClientsRepository(@NonNull Context context) {
        return ClientsRepository.getInstance();
    }

}
