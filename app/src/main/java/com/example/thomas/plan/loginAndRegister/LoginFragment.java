package com.example.thomas.plan.loginAndRegister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.thomas.plan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private EditText etUsername, etPassword;
    private Button buttonLogin;
    private LoginViewModel loginViewModel;

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private final String USERNAME = "pospecnik@seznam.cz";
    private final String PASSWORD = "planek";

    private String username;
    private String password;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        loginViewModel = LoginActivity.obtainViewModel(getActivity());

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        etUsername = getView().findViewById(R.id.username);
        etPassword = getView().findViewById(R.id.password);

        etUsername.setText(USERNAME);
        etPassword.setText(PASSWORD);

        buttonLogin = getView().findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                //todo vylepsit
                loginViewModel.performLogin(username, password);
                tryToSignIn(username,password);
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    private void tryToSignIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            loginViewModel.getLoginState().setValue(LoginState.ERROR_UNKNOWN);
                            loginViewModel.getErrorMessage().setValue(task.getException().getMessage());
                        }
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loginViewModel.getLoginState().setValue(LoginState.RESULT_OK);

                        } else {
                            // If sign in fails, display a message to the user.
                            loginViewModel.getLoginState().setValue(LoginState.ERROR_CREDENTIALS);
                        }
                        //hideDialog();
                    }

                });
    }
}




