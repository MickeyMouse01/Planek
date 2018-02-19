package com.example.thomas.plan.Clients;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pospe on 18.02.2018.
 */

public class ClientsListBinding {


    @BindingAdapter("app:items")
    public static void setItems(ListView listView, List<Client> items) {
        ListOfClientsAdapter adapter = (ListOfClientsAdapter) listView.getAdapter();
        if (adapter != null)
        {
            adapter.replaceData(items);
        }
    }
}
