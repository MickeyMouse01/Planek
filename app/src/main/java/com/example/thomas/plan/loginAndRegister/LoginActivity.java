package com.example.thomas.plan.loginAndRegister;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.data.Models.Nurse;
import com.example.thomas.plan.previewTasks.PreviewTasksActivity;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final int RC_SIGN_IN = 123;
    public static Client LOGGED_CLIENT;
    private final int NURSE_LOGIN = 1;
    private final int CLIENT_LOGIN = 0;
    private int actualFragment = 0;

    private Button switchButton;
    private LoginViewModel loginViewModel;
    private Boolean isNewUser = false;

    public static LoginViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        LoginViewModel viewModel = ViewModelProviders.of(activity, factory).get(LoginViewModel.class);
        return viewModel;
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
                Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ClientsActivity.class);
            intent.putExtra("userId", loginViewModel.getLoggedUser().getValue());
            intent.putExtra("isNewUser", isNewUser);
            startActivity(intent);


        } else {
            loginViewModel.getLoggedClient().observe(this, new Observer<Client>() {
                @Override
                public void onChanged(@Nullable Client client) {
                    LOGGED_CLIENT = client;
                    startPreviewTasksActivity();
                }
            });
        }
    }

    private void startPreviewTasksActivity() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PreviewTasksActivity.class);
        //todo predelat
        intent.putExtra("PlanId", "aa6f7b62-6964-40b3-9616-396bf76b0f98");
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
                /*List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);*/
                //todo jen abych se furt nemusel prihlasovat...
                loginViewModel.getLoginState().setValue(LoginState.RESULT_OK);
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
                            newNurse.setEmail(user.getEmail());
                            newNurse.setName(user.getDisplayName());
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


