package com.kawo.museapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;

import android.os.Handler;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kawo.museapp.dashboard.MainDashBoard;


public class PathActivity extends AppCompatActivity {

    GridLayout grid;
    Animation bottomAnimation;
    final Handler h = new Handler();
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    LinearLayout linearLayout;
    private TextView choosePathText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //Hooks
        grid = findViewById(R.id.grid1);
        //Setting Animations
        grid.setAnimation(bottomAnimation);
        SharedPreferences pref = PathActivity.this.getSharedPreferences("shared", 0);
        SharedPreferences.Editor editor = pref.edit();
        boolean firstRun = pref.getBoolean("firstRun", true);
        choosePathText = findViewById(R.id.textGrid);
        if(firstRun==false) choosePathText.setText(R.string.select_your_path_type);


        linearLayout = (LinearLayout) findViewById(R.id.background);
        cardView1 = (CardView) findViewById(R.id.card1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PathActivity.this, SubPathActivity.class);
                Pair[] pair = new Pair[1];
                pair[0]= new Pair<View, String>(cardView1, "trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PathActivity.this, pair);
                intent.putExtra("Type", "PIECE_COMPOSER");
                startActivity(intent, options.toBundle());
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 100);
            }
        });

        cardView2 = (CardView) findViewById(R.id.card2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PathActivity.this, SubPathActivity.class);
                Pair[] pair = new Pair[1];
                pair[0]= new Pair<View, String>(cardView2, "trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PathActivity.this, pair);
                intent.putExtra("Type", "PIECE_PERIOD");
                startActivity(intent, options.toBundle());
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 100);
            }
        });

        cardView3 = (CardView) findViewById(R.id.card3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PathActivity.this, SubPathActivity.class);
                Pair[] pair = new Pair[1];
                pair[0]= new Pair<View, String>(cardView3, "trans1");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(PathActivity.this, pair);
                intent.putExtra("Type", "PIECE_TYPE");
                startActivity(intent, options.toBundle());
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 100);
            }
        });


        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);

        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PathActivity.this, MainDashBoard.class);
        startActivity(intent);
            finish();
    }



}