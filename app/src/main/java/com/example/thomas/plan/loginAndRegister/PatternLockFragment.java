package com.example.thomas.plan.loginAndRegister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.thomas.plan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class PatternLockFragment extends Fragment {

    private static final String crUsername= "admin@admin.cz";
    private static final String crPassword = "5YpA5EDEI7OdGpkZ";

    private LoginViewModel loginViewModel;
    private PatternLockView mPatternLockView;
    private EditText usernameTextField;
    private FirebaseAuth mAuth;

    public PatternLockFragment() {
        // Required empty public constructor
    }

    public static PatternLockFragment newInstance() {
        return new PatternLockFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        loginViewModel = LoginActivity.obtainViewModel(getActivity());

        return inflater.inflate(R.layout.fragment_pattern_lock, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPatternLockView = getView().findViewById(R.id.pattern_lock_view);
        usernameTextField = getView().findViewById(R.id.login_username_pattern);
        mAuth = FirebaseAuth.getInstance();

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

                if (usernameTextField.getText().toString().isEmpty()) {
                    usernameTextField.setError("Uživatelské jméno musí být vyplněno");
                    mPatternLockView.clearPattern();
                } else
                    tryToSignIn(crUsername,crPassword,lock);
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void tryToSignIn(String email, String password, final String lock) {

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
                            loginViewModel.performLoginClient(usernameTextField.getText().toString(), lock);

                        } else {
                            // If sign in fails, display a message to the user.
                            loginViewModel.getLoginState().setValue(LoginState.ERROR_CREDENTIALS);
                        }
                        //hideDialog();
                    }

                });
    }
}
