package com.kawo.museapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kawo.museapp.dashboard.MainDashBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.view.View;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);


    }

    public void onBackPressed() {
        Intent myIntent = new Intent(AppInfo.this, MainDashBoard.class);
        AppInfo.this.startActivity(myIntent);
        finish();
    }


}