package com.example.thomas.plan.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.thomas.plan.LoginState;
import com.example.thomas.plan.LoginViewModel;
import com.example.thomas.plan.Presenter.LoginPresenter;
import com.example.thomas.plan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.arch.lifecycle.*;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText etUsername, etPassword;
    Button buttonLogin;
    LoginPresenter mLoginPresenter;
    private LoginViewModel loginViewModel;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private static final String FIREBASE_EMAIL = "planningklic@gmail.com" ;
    private static final String FIREBASE_PASSWORD = "77QTg72MHdGdPUjy";


    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(this);

       // mLoginPresenter = new LoginModel(LoginActivity.this);
        loginViewModel = new LoginViewModel();
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

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        loginViewModel.performLogin(username,password);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
             Log.d(TAG,"blash");
            }
        });


    }

    private void userLogin(LoginState loginState) {
        switch (loginState) {
            case RESULT_OK:
                loginSuccess();
                break;
            case ERROR_CREDENTIALS:
                loginError();
                break;
            case ERROR_VALIDATIONS:
                loginValidations();
                break;
        }

    }




    private void loginValidations() {

        Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
    }


    private void loginSuccess() {

        Toast.makeText(getApplicationContext(), "Login succes", Toast.LENGTH_SHORT).show();
        signIn(FIREBASE_EMAIL, FIREBASE_PASSWORD);
        Log.d("TAG", "prihalseni");
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 2);
        startActivity(intent);
    }


    private void loginError() {
        signIn(FIREBASE_EMAIL, FIREBASE_PASSWORD);
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
       // showDialog("Loading");

        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            // mStatusTextView.setText(R.string.auth_failed);
                            Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    //    hideDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
}
