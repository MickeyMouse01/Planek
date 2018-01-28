package com.example.thomas.plan.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.plan.Client;
import com.example.thomas.plan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 27.01.2018.
 */

public class ListOfClientsAdapter extends ArrayAdapter {

    private final Activity context;
    private final List<Client> listOfClients;

    public ListOfClientsAdapter(Activity context,
                                List<Client> listOfClients) {
        super(context, R.layout.list_of_clients, listOfClients);
        this.listOfClients = listOfClients;
        this.context = context;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_of_clients, null, true);
        TextView firstName = (TextView) rowView.findViewById(R.id.firstName);
        TextView surname = (TextView) rowView.findViewById(R.id.surname);

        firstName.setText(listOfClients.get(position).getName());
        surname.setText(listOfClients.get(position).getSurname());

        return rowView;
    }
    public void updateListOfClients(List<Client> newlist) {
        listOfClients.clear();
        listOfClients.addAll(newlist);
        this.notifyDataSetChanged();
    }
}
