package com.example.thomas.plan.loginAndRegister;

import android.os.Bundle;
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

import java.util.List;


public class PatternLockFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private PatternLockView mPatternLockView;
    private EditText usernameTextField;

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
                    loginViewModel.performLoginClient(usernameTextField.getText().toString(), lock);
            }

            @Override
            public void onCleared() {

            }
        });
    }
}
