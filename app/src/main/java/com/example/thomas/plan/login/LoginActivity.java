package com.example.thomas.plan.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.andrognito.patternlockview.PatternLockView;
import com.example.thomas.plan.Activities.BaseActivity;
import com.example.thomas.plan.ActivityUtils;
import com.example.thomas.plan.Clients.ClientsActivity;

import com.example.thomas.plan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.arch.lifecycle.*;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    private Button switchButton;
    private boolean whichFragment;
    private LoginViewModel loginViewModel;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        whichFragment = true;
        switchButton = findViewById(R.id.switch_fragment);
        loginViewModel = obtainViewModel(this);

        setupViewFragment(whichFragment);
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
        }
    }

    private void loginValidations() {
        Toast.makeText(this,
                "Please enter email and password", Toast.LENGTH_SHORT).show();
    }

    private void startActivity(){
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ClientsActivity.class);
        intent.putExtra("message", 2);
        startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    private void setupViewFragment(boolean frame) {
        if (frame) {
            PatternLockFragment patternFragment = PatternLockFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), patternFragment, R.id.contentFrame);
            switchButton.setText("Vychovatel");
        } else {

            LoginFragment loginFragment = LoginFragment.newInstance();
            ActivityUtils.replaceFragmentInActivity(
                    getSupportFragmentManager(), loginFragment, R.id.contentFrame);
            switchButton.setText("Klient");
        }
    }

    public static LoginViewModel obtainViewModel(FragmentActivity activity) {
        LoginViewModel viewModel = ViewModelProviders.of(activity).get(LoginViewModel.class);
        return viewModel;
    }

    @Override
    public void onClick(View view) {
        whichFragment = !whichFragment;
        setupViewFragment(whichFragment);
    }
}

