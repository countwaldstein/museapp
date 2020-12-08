package com.kawo.museapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.kawo.museapp.dashboard.MainDashBoard;
import com.kawo.museapp.firstpath.ComRecyclerItem;
import com.kawo.museapp.firstpath.FirstTimeActivity;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    //Hooks
    View ludwig;
    TextView museapp, slogan;

    //Animations
    Animation topAnimation, bottomAnimation, centerAnimation;

    private static final String TAG = "SplashScreen";

    final Handler h = new Handler();
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        centerAnimation = AnimationUtils.loadAnimation(this, R.anim.center_animation);

        //Hooks
        ludwig = findViewById(R.id.ludwig);
        museapp = findViewById(R.id.museapp);
        slogan = findViewById(R.id.slogan);

        //Setting Animations
        ludwig.setAnimation(topAnimation);
        museapp.setAnimation(centerAnimation);
        slogan.setAnimation(bottomAnimation);



        h.postDelayed(new Runnable() {

            SharedPreferences pref = SplashScreen.this.getSharedPreferences("shared", 0);
            SharedPreferences.Editor editor = pref.edit();
            boolean firstRun = pref.getBoolean("firstRun", true);

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override

            public void run() {

                if (firstRun) {
                    Intent intent = new Intent(SplashScreen.this, FirstTimeActivity.class);
                    finish();
                    startActivity(intent);

                } else {
                    Log.i("onCreate: ", "second time");
                    Intent intent = new Intent(SplashScreen.this, MainDashBoard.class);
                    finish();
                    startActivity(intent);

                }

            }

        }, 2000);


    }


}