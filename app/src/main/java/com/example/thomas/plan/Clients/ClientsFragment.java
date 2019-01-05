package com.example.thomas.plan.Clients;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thomas.plan.ActionItemListener;
import com.example.thomas.plan.R;
import com.example.thomas.plan.activities.MainActivity;
import com.example.thomas.plan.data.Models.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pospe on 15.02.2018.
 */


public class ClientsFragment extends Fragment {

    private MainViewModel mMainViewModel;
    private ListOfClientsAdapter mClientsAdapter;
    private AlertDialog.Builder alertDialog;
    private ListView listView;
    private ProgressDialog mProgressDialog;

    public ClientsFragment() {
        // Requires empty public constructor
    }

    public static ClientsFragment newInstance() {
        return new ClientsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainViewModel.getClients();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.clients_fragment, null);
        listView = view.findViewById(R.id.clients_list);

        mMainViewModel = MainActivity.obtainViewModel(getActivity());
        mMainViewModel.getClients().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(@Nullable List<Client> clients) {
                if (mClientsAdapter != null) {
                    mClientsAdapter.replaceData(clients);
                }
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
        alertDialog = createConfirmDialog();
    }

    private AlertDialog.Builder createConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_dialog_message);
        builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder;
    }

    private void setupListAdapter() {
        ActionItemListener<Client> actionItemListener = new ActionItemListener<Client>() {
            @Override
            public void onCheckedClick(Client item) {

            }

            @Override
            public void onItemClick(Client item) {
                mMainViewModel.previewClient().setValue(item.getUniqueID());
            }

            @Override
            public void onItemInfoClick(Client item) {
                mMainViewModel.viewClient().setValue(item.getUniqueID());
            }

            @Override
            public void onItemDeleteClick(final Client item) {
                alertDialog.setPositiveButton("Ano", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMainViewModel.removeClient(item.getUniqueID());
                    }
                }).show();

            }
        };
        mClientsAdapter = new ListOfClientsAdapter(
                new ArrayList<Client>(0), actionItemListener
        );
        listView.setAdapter(mClientsAdapter);
    }

    private void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
