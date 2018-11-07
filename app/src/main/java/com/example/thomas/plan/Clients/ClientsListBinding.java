package com.example.thomas.plan.Clients;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import com.example.thomas.plan.data.Models.Client;

/**
 * Created by pospe on 18.02.2018.
 */

public class ClientsListBinding {

    @BindingAdapter("app:clients")
    public static void setClients(ListView listView, ObservableList<Client> items) {
        ListOfClientsAdapter adapter = (ListOfClientsAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}


