package com.example.thomas.plan.addEditClient;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.Common.Enums;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddEditClientActivity extends BaseActivity implements View.OnClickListener {

    private EditText mFirstName, mUserName;
    private EditText mSurname;
    private Spinner sTypeOfGroup;
    private Button changePassword;
    private String actualLock = "";
    private TextView txtForPassword;
    private String idOfClient;
    private Client editedClient;
    private int countForSave = 0;

    private PatternLockView mPatternLockView;
    private AddEditClientViewModel mViewModel;

    private static AddEditClientViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AddEditClientViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mFirstName = findViewById(R.id.add_firstName);
        mSurname = findViewById(R.id.add_surname);
        sTypeOfGroup = findViewById(R.id.type_of_group);
        Button save = findViewById(R.id.add_save_button);
        txtForPassword = findViewById(R.id.add_password_pattern_text);
        mPatternLockView = findViewById(R.id.add_pattern_lock_view);
        changePassword = findViewById(R.id.change_password);
        mUserName = findViewById(R.id.add_username);
        addListenerForPattern();
        mViewModel = obtainViewModel(this);
        idOfClient = intent.getStringExtra("idClient");

        if (idOfClient != null) {
            setTitle("Upravit klienta");
            mViewModel.getEditedClient(idOfClient).observe(this, new Observer<Client>() {
                @Override
                public void onChanged(@Nullable Client client) {
                    initializeClient(client);
                }
            });
        }

        save.setOnClickListener(this);

        mViewModel.onClientSaved().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                finishThisActivity();
            }
        });

    }

    private void finishThisActivity() {
        hideDialog();
        showSuccessToast("Klient byl úspěšně uložen");
        finish();
    }

    private void initializeClient(Client client) {
        editedClient = client;
        mFirstName.setText(client.getFirstName());
        mSurname.setText(client.getSurname());
        sTypeOfGroup.setSelection(client.getTypeOfGroup().getCode());
        mUserName.setText(client.getUsername());
        txtForPassword.setVisibility(View.INVISIBLE);
        mPatternLockView.setVisibility(View.INVISIBLE);
        changePassword.setVisibility(View.VISIBLE);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtForPassword.setVisibility(View.VISIBLE);
                mPatternLockView.setVisibility(View.VISIBLE);
                changePassword.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_add_edit_client;
    }

    private void addListenerForPattern() {
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String lock = PatternLockUtils.patternToString(mPatternLockView, pattern);
                if (!actualLock.isEmpty() && !actualLock.equals(lock)) {
                    mPatternLockView.clearPattern();
                    actualLock = "";
                    countForSave = 0;
                    txtForPassword.setText("Heslo se neshoduje, zadejte znovu!");

                    Toast.makeText(AddEditClientActivity.this,
                            "Heslo se neshoduje, zadejte znovu!",
                            Toast.LENGTH_SHORT).show();

                } else {
                    countForSave++;
                    txtForPassword.setText("Zadejte znovu pro potvrzeni!");
                    actualLock = lock;
                }

                if (countForSave == 2) {
                    countForSave = 0;
                    actualLock = lock;
                    txtForPassword.setText("Heslo úspěšně vybráno!");
                }
            }

            @Override
            public void onCleared() {
            }
        });
    }

    private void addClient() {
        showDialog("Ukládání");
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        int typeOfGroup = sTypeOfGroup.getSelectedItemPosition();
        String userName = mUserName.getText().toString();
        String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                .format(Calendar.getInstance().getTime());
        Client newClient = new Client(firstName, surname, Enums.TypeOfGroup.values()[typeOfGroup]);
        newClient.setCreatedDate(dateTime);
        newClient.setUsername(userName);
        newClient.setPassword(actualLock);
        mViewModel.saveClient(newClient);
    }

    private void editClient() {
        String firstName = mFirstName.getText().toString();
        String surname = mSurname.getText().toString();
        int typeOfGroup = sTypeOfGroup.getSelectedItemPosition();
        String userName = mUserName.getText().toString();

        editedClient.setFirstName(firstName);

        editedClient.setSurname(surname);
        editedClient.setTypeOfGroup(Enums.TypeOfGroup.values()[typeOfGroup]);
        editedClient.setUsername(userName);
        editedClient.setPassword(actualLock);
        mViewModel.saveClient(editedClient);
    }

    private boolean requiredFieldsAreFilled() {
        boolean isFilled = true;
        if (mFirstName.getText().toString().isEmpty()) {
            mFirstName.setError("Toto pole je povinné");
            isFilled = false;
        } else if (mSurname.getText().toString().isEmpty()) {
            mSurname.setError("Toto pole je povinné");
            isFilled = false;
        } else if (mUserName.getText().toString().isEmpty()) {
            mUserName.setError("Toto pole je povinné");
            isFilled = false;
        }
        return isFilled;
    }


    @Override
    public void onClick(View v) {
        if (idOfClient == null) {
            if (requiredFieldsAreFilled()) {
                addClient();
            }
        } else {
            editClient();
        }
    }
}
