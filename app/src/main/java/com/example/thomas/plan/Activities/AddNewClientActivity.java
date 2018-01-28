package com.example.thomas.plan.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thomas.plan.Client;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.Model.AddNewClientModel;
import com.example.thomas.plan.Presenter.AddNewClientPresenter;
import com.example.thomas.plan.R;
import com.example.thomas.plan.View.AddNewClientView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddNewClientActivity extends BaseActivity implements View.OnClickListener, AddNewClientView {

    private EditText mFirstName;
    private EditText mSurname;
    private Spinner sTypeOfGroup;
    private Button save;

    private Client newClient;

    private ArrayList<Client> lisOfClients;

    private Intent intent;

    private AddNewClientPresenter addNewClientPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mFirstName = findViewById(R.id.add_firstName);
        mSurname = findViewById(R.id.add_surname);
        sTypeOfGroup = findViewById(R.id.type_of_group);

        save = findViewById(R.id.add_save_button);
        save.setOnClickListener(this);

        this.intent = intent;

        Bundle data = this.getIntent().getBundleExtra("listOfClients");
        lisOfClients = data.getParcelableArrayList("listOfClients");

        addNewClientPresenter = new AddNewClientModel(AddNewClientActivity.this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_new_client;
    }

    @Override
    public void onClick(View view) {
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        String typeOfGroup = sTypeOfGroup.getSelectedItem().toString();
        addNewClientPresenter.performAddNewClient(firstName,surname, Enums.TypeOfGroup.GROUPA);

    }


    @Override
    public void addSuccess(String firsName, String surname, Enums.TypeOfGroup typeOfGroup) {
        newClient = new Client(firsName,surname,typeOfGroup);
        lisOfClients.add(newClient);

        Bundle data = new Bundle();
        data.putParcelableArrayList("listOfClients", lisOfClients);

        Intent intent = new Intent(this, AddNewClientActivity.class);
        intent.putExtra("listOfClients",data);

        setResult(1,intent);
        finish();
    }

    @Override
    public void addError() {

    }

    @Override
    public void addValidations() {

    }


}
