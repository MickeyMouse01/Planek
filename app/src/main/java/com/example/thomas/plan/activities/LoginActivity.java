package com.example.thomas.plan.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Common.Enums.Day;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.Common.LoginState;
import com.example.thomas.plan.viewmodels.LoginViewModel;
import com.example.thomas.plan.fragments.PatternLockFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 123;
    public static Client LOGGED_CLIENT;
    private final int NURSE_LOGIN = 1;
    private final int CLIENT_LOGIN = 0;
    private int actualFragment = 0;

    private Button switchButton;
    private LoginViewModel loginViewModel;

    public static LoginViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(LoginViewModel.class);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        switchButton = findViewById(R.id.switch_fragment);
        loginViewModel = obtainViewModel(this);

        setupView(CLIENT_LOGIN);
        switchButton.setOnClickListener(this);

        loginViewModel.getLoginState().observe(this, new Observer<LoginState>() {
            @Override
            public void onChanged(@Nullable LoginState loginState) {
                userLogin(loginState);
            }
        });

    }

    private void userLogin(LoginState loginState) {
        switch (loginState) {
            case RESULT_OK:
                startActivity();
                break;
            case ERROR_VALIDATIONS:
                loginValidations();
                break;
            case ERROR_CREDENTIALS:
                showErrorToast("Přihlášení se nezdařilo. Špatné jméno nebo heslo");
                break;
            case ERROR_UNKNOWN:
                Toast.makeText(this, loginViewModel.getErrorMessage().getValue(),
                        Toast.LENGTH_LONG).show();
        }
    }

    private void loginValidations() {
        Toast.makeText(this,
                "Please enter email and password", Toast.LENGTH_SHORT).show();
    }


    private void startActivity() {
        if (actualFragment == NURSE_LOGIN) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userId", loginViewModel.getLoggedUser().getValue());
            Boolean isNewUser = false;
            intent.putExtra("isNewUser", isNewUser);
            startActivity(intent);


        } else {
            loginViewModel.getLoggedClient().observe(this, new Observer<Client>() {
                @Override
                public void onChanged(@Nullable Client client) {
                    LOGGED_CLIENT = client;
                    if (client != null) {
                        int day = ActivityUtils.getActualDay();
                        startPreviewTasksActivity(client
                                .getPlansForDate()
                                .get(Day.values()[day].getNameOfDay()), client.getUniqueID());
                    }
                }
            });
        }
    }

    private void startPreviewTasksActivity(String planId, String clientId) {
        showSuccessToast("Přihlášen");
        Intent intent = new Intent(this, ClientScreenActivity.class);
        intent.putExtra("PlanId", planId);
        intent.putExtra("ClientId", clientId);
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    private void setupView(int frame) {
        switch (frame) {
            case CLIENT_LOGIN: {
                PatternLockFragment patternFragment = PatternLockFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(
                        getSupportFragmentManager(), patternFragment, R.id.contentFrame);
                switchButton.setText("Vychovatel");
                actualFragment = CLIENT_LOGIN;
                break;
            }
            case NURSE_LOGIN: {
                actualFragment = NURSE_LOGIN;
                List<AuthUI.IdpConfig> providers = Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
                //loginViewModel.getLoginState().setValue(LoginState.RESULT_OK);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                loginViewModel.getNurseByEmail(user.getEmail());

                loginViewModel.getLoggedNurse().observe(this, new Observer<Nurse>() {
                    @Override
                    public void onChanged(@Nullable Nurse nurse) {
                        if (nurse == null) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String dateTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss")
                                    .format(Calendar.getInstance().getTime());
                            Nurse newNurse = new Nurse(user.getUid());
                            String editedNameAndSurname = user.getDisplayName();
                            String[] split = editedNameAndSurname.split("\\s+");
                            String name = split[0];
                            String surname = split[1];
                            newNurse.setEmail(user.getEmail());
                            newNurse.setName(name);
                            newNurse.setSurname(surname);
                            newNurse.setCreatedDate(dateTime);
                            loginViewModel.saveNurse(newNurse);
                            loginViewModel.getLoginState().setValue(LoginState.RESULT_OK);
                        } else {
                            loginViewModel.getLoginState().setValue(LoginState.RESULT_OK);
                        }
                    }
                });

            } else if (response != null) {
                Toast.makeText(this,
                        "Authentication Failed: " + response.getError().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (actualFragment == CLIENT_LOGIN) {
            setupView(NURSE_LOGIN);
        } else {
            setupView(CLIENT_LOGIN);
        }
    }
}


