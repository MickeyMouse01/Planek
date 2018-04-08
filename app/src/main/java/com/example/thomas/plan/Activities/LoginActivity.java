package com.example.thomas.plan.Activities;

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


import com.example.thomas.plan.Clients.ClientsActivity;
import com.example.thomas.plan.login.LoginState;
import com.example.thomas.plan.login.LoginViewModel;

import com.example.thomas.plan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.arch.lifecycle.*;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etUsername, etPassword;
    private Button buttonLogin;
    private LoginViewModel loginViewModel;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private final String USERNAME = "pospecnik@seznam.cz";
    private final String PASSWORD = "planek";


    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private String username;
    private String password;


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        etUsername.setText(USERNAME);
        etPassword.setText(PASSWORD);

        buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(this);

       // mLoginPresenter = new LoginModel(LoginActivity.this);
        loginViewModel = obtainViewModel(this);
        loginViewModel.getLoggedUser().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        loginViewModel.getLoginState().observe(this, new Observer<LoginState>() {
            @Override
            public void onChanged(@Nullable LoginState loginState) {
                userLogin(loginState);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        //jen pro rychle spousteni
       // buttonLogin.performClick();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    public static LoginViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel

        LoginViewModel viewModel = ViewModelProviders.of(activity).get(LoginViewModel.class);
        return viewModel;
    }


    @Override
    public void onClick(View view) {
        username = etUsername.getText().toString();
        password = etPassword.getText().toString();

        loginViewModel.performLogin(username,password);
    }

    private void userLogin(LoginState loginState) {
        switch (loginState) {
            case RESULT_OK:
                tryToSignIn(username,password);
                break;
            case ERROR_VALIDATIONS:
                loginValidations();
                break;
        }

    }

    private void loginValidations() {
        etUsername.setError("Enter valid email address");
        etPassword.setError("bsads");
        Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
    }


    private void tryToSignIn(String email, String password) {
        showDialog("Loading");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                        }
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("mojeID",user.getUid());
                            Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ClientsActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, 2);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }

                });
    }
}
