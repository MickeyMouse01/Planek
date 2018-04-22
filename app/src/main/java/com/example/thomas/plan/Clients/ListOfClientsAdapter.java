package com.example.thomas.plan.Clients;

import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.databinding.ClientItemBinding;

import java.util.List;

/**
 * Created by Thomas on 27.01.2018.
 */

public class ListOfClientsAdapter extends BaseAdapter {

    private ClientsViewModel mClientsViewModel;
    private List<Client> mClients;


    public ListOfClientsAdapter(List<Client> clients,
                                ClientsViewModel clientsViewModel) {
        mClientsViewModel = clientsViewModel;
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
    public View getView(int position, final View view, final ViewGroup viewGroup) {
        ClientItemBinding binding;
        if (view == null) {
            // Inflate
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

            // Create the binding
            binding = ClientItemBinding.inflate(inflater, viewGroup, false);
        } else {
            // Recycling view
            binding = DataBindingUtil.getBinding(view);
        }

        ClientItemUserActionsListener userActionsListener = new ClientItemUserActionsListener() {
            @Override
            public void onClientClicked(Client client) {

            }

            @Override
            public void onClientInfoClicked(Client client) {
                mClientsViewModel.viewClient().setValue(client.getUniqueID());
            }

            //todo nejaky modal okno na potvrzovani
            @Override
            public void removeClient(Client client) {
                mClientsViewModel.removeClient(client.getUniqueID());
            }
        };

        binding.setClient(mClients.get(position));

        binding.setListener(userActionsListener);

        binding.executePendingBindings();
        return binding.getRoot();
    }
}
