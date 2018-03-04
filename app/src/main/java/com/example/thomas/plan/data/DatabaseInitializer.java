package com.example.thomas.plan.data;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.thomas.plan.Common.Enums;

import java.util.List;
import java.util.UUID;

/**
 * Created by Tomas on 04-Mar-18.
 */

public class DatabaseInitializer  {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Client addUser(final AppDatabase db, Client client) {
        db.clientDao().insertAll(client);
        return client;
    }

    private static void populateWithTestData(AppDatabase db) {
        Client client = new Client();
        client.setName("MujTest");
        client.setSurname("Anoj");
        client.setUniqueID(UUID.randomUUID().toString());
        client.setTypeOfGroup(Enums.TypeOfGroup.GROUPA);
        addUser(db, client);
        Log.d("Muj Tag", "Funguje");

        List<Client> userList = db.clientDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
