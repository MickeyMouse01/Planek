package com.example.thomas.plan.Clients;

import com.example.thomas.plan.data.Models.Client;

/**
 * Created by pospe on 18.02.2018.
 */

public interface ItemUserActionsListener {

    void onItemClicked(Client client);
    void onItemInfoClicked(Client client);
    void removeItem(Client client);
}
