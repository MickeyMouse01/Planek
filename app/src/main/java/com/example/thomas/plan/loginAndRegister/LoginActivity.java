package com.example.thomas.plan.loginAndRegister;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.R;
import com.example.thomas.plan.ViewModelFactory;
import com.example.thomas.plan.data.Models.Client;
import com.example.thomas.plan.previewTasks.PreviewTasksActivity;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static Client LOGGED_CLIENT;
    private static final int RC_SIGN_IN = 123;

    private final int NURSE_LOGIN = 1;
    private final int CLIENT_LOGIN = 0;
    private final int REGISTER = 2;
    private final int FORGOTTEN_PASSWORD = 3;
    private int actualFragment = 0;

    private Button switchButton;
    private LoginViewModel loginViewModel;

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

        setupViewFragment(CLIENT_LOGIN);
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
            intent.putExtra("message", 2);
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

    private void setupViewFragment(int frame) {

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
                LoginFragment loginFragment = LoginFragment.newInstance();
                ActivityUtils.replaceFragmentInActivity(
                        getSupportFragmentManager(), loginFragment, R.id.contentFrame);
                switchButton.setText("Klient");
                actualFragment = NURSE_LOGIN;
                break;
            }
            case REGISTER: {

                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
            case FORGOTTEN_PASSWORD: {
                Log.d("Ahoj","heslo");
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.switch_fragment: {
                if (actualFragment == CLIENT_LOGIN) {
                    setupViewFragment(NURSE_LOGIN);
                } else {
                    setupViewFragment(CLIENT_LOGIN);
                }
                break;
            }
            case R.id.register: {
                setupViewFragment(REGISTER);
                break;
            }
            case R.id.forgetPassword: {
                setupViewFragment(FORGOTTEN_PASSWORD);
                break;
            }
        }
    }
}

