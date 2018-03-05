package com.example.thomas.plan.data.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.thomas.plan.data.Client;

import java.util.List;

/**
 * Created by Tomas on 25-Feb-18.
 */

@Dao
public interface ClientDao {

    @Query("SELECT * FROM client")
    List<Client> getAll();

    @Query("SELECT * FROM client where first_name LIKE  :Name AND sur_name LIKE :Surname")
    Client findByName(String Name, String Surname);

    @Query("SELECT COUNT(*) from client")
    int countClients();

    @Insert
    void insertAll(Client... client);

    @Delete
    void delete(Client client);
}
