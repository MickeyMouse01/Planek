package com.example.thomas.plan.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.plan.R;

/**
 * Created by Thomas on 24.01.2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        onViewReady(savedInstanceState, getIntent());
    }

    protected abstract int getContentView();

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        //To be used by child activities
    }

    protected void showDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void showToast(String message) {
        View toastLayout = getLayoutInflater().inflate(R.layout.toast_layout, null);

        TextView text = toastLayout.findViewById(R.id.text_of_toast);
        text.setText(message);

        toast = new Toast(getApplicationContext());
        toast.setView(toastLayout);
        toast.setDuration(Toast.LENGTH_LONG);
    }

    protected void showErrorToast(String message) {
        showToast(message);
        toast.getView().setBackgroundColor(Color.RED);
        toast.show();
    }

    protected void showInfoToast(String message) {
        showToast(message);
        toast.getView().setBackgroundColor(Color.BLUE);
        toast.show();
    }

    public void showSuccessToast(String message) {
        showToast(message);
        toast.getView().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_toast_success));
        toast.show();
    }

}
