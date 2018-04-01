package com.example.thomas.plan.Clients;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.ListView;

import com.example.thomas.plan.data.Models.Client;

import java.util.List;

/**
 * Created by pospe on 18.02.2018.
 */

public class ClientsListBinding {

    @BindingAdapter("app:items")
    public static void setItems(ListView listView, ObservableList<Client> items) {
        ListOfClientsAdapter adapter = (ListOfClientsAdapter) listView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

}
