package com.example.thomas.plan.Clients;

import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.R;
import com.example.thomas.plan.data.Models.Client;

import java.util.List;

/**
 * Created by Thomas on 27.01.2018.
 */

public class ListOfClientsAdapter extends BaseAdapter {

    private List<Client> mClients;
    private View clientView;
    private ImageButton infoButton, deleteButton;
    private ConstraintLayout constraintLayout;
    private TextView nameOfClient,surname,typeOfGroup;
    private ActionItemListener actionItemListener;

    public ListOfClientsAdapter(List<Client> clients,
                                ActionItemListener actionListener) {
        this.actionItemListener = actionListener;
        mClients = clients;

    }

    public void replaceData(List<Client> clients) {
        mClients = clients;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mClients != null ? mClients.size() : 0;
    }

    @Override
    public Client getItem(int position) {
        return mClients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        clientView = inflater.inflate(R.layout.client_item, null);

        nameOfClient = clientView.findViewById(R.id.client_item_name);
        surname = clientView.findViewById(R.id.client_item_surname);
        typeOfGroup = clientView.findViewById(R.id.client_item_typeOfgroup);
        infoButton = clientView.findViewById(R.id.client_item_info_button);
        deleteButton = clientView.findViewById(R.id.client_item_delete_button);
        constraintLayout = clientView.findViewById(R.id.client_item_linear);

        nameOfClient.setText(mClients.get(position).getFirstName());
        surname.setText(mClients.get(position).getSurname());
        typeOfGroup.setText(mClients.get(position).getTypeOfGroup().name());

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionItemListener.onItemClick(mClients.get(position));
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionItemListener.onItemInfoClick(mClients.get(position));
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionItemListener.onItemDeleteClick(mClients.get(position));
            }
        });
        return clientView;
    }
}
