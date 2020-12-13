package com.kawo.museapp.dashboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;
import com.kawo.museapp.AllPiecesList;
import com.kawo.museapp.DataBaseHelper;
import com.kawo.museapp.PathView;
import com.kawo.museapp.rating.PieceRatingActivity;

import com.kawo.museapp.R;
import com.kawo.museapp.firstpath.ComRecyclerItem;
import com.kawo.museapp.PathActivity;
import com.kawo.museapp.SubPathActivity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;

public class MainDashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SubPathActivity mSubPathActivity;
    private ArrayList<ComRecyclerItem> comRecyclerItemList;
    ComRecyclerItem mComRecyclerItem;
    RecyclerView composersRecycler;
    RecyclerView typeRecycler;
    RecyclerView periodRecycler;
    DashComposerAdapter cadapter;
    DashComposerAdapter tadapter;
    DashComposerAdapter padapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    private ArrayList<DashBoardCardType> dashBoardCardTypeArrayList;
    private SQLiteDatabase mDataBase;
    private static final String TAG = "DashBoardActivity";
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;
    private DataBaseHelper mDataBaseHelper;
    final PieceRatingActivity pr2 = new PieceRatingActivity();

    ImageView addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);
        composersRecycler = findViewById(R.id.db_recycler_composers);
        typeRecycler = findViewById(R.id.db_recycler_types);
        periodRecycler = findViewById(R.id.db_recycler_periods);

        addButton = findViewById(R.id.addpathbutton);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.dash_navigationview);
        menuIcon = findViewById(R.id.nav_open_button_dash);
        contentView = findViewById(R.id.content_dash);
        mDataBaseHelper = new DataBaseHelper(MainDashBoard.this);
        mDataBase = mDataBaseHelper.getWritableDatabase();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainDashBoard.this, PathActivity.class);
                MainDashBoard.this.startActivity(myIntent);
                finish();
            }
        });


        if (getIntent().getExtras() == null) {
        } else {
            Bundle bundle = getIntent().getExtras();
            String deletemessage = bundle.getString("DeleteM");

            Snackbar snackbar = Snackbar.make(contentView, deletemessage, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        composerDashRecycler();
        typeDashRecycler();
        periodDashRecycler();
        navigation_drawer();

    }

    private void composerDashRecycler() {
        composersRecycler.setHasFixedSize(true);
        composersRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<DashBoardCardType> arrayComp = getPathSubtypeDash("PIECE_COMPOSER");

        cadapter = new DashComposerAdapter(arrayComp);
        composersRecycler.setAdapter(cadapter);


        cadapter.setOnItemClickListener(new DashComposerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DashBoardCardType dbtest = getPathSubtypeDash("PIECE_COMPOSER").get(position);
                String composer = dbtest.title;
                Log.i("how many", Integer.toString(howManyUnratedLeft("PIECE_COMPOSER", composer)));
                Intent myIntent = new Intent(MainDashBoard.this, PathView.class);
                myIntent.putExtra("Type", "PIECE_COMPOSER");
                myIntent.putExtra("Subtype", composer);
                MainDashBoard.this.startActivity(myIntent);


            }
        });
        if (arrayComp.size() == 0) composersRecycler.setVisibility(View.GONE);
    }

    private void typeDashRecycler() {
        typeRecycler.setHasFixedSize(true);
        typeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<DashBoardCardType> arrayType = getPathSubtypeDash("PIECE_TYPE");

        tadapter = new DashComposerAdapter(arrayType);
        typeRecycler.setAdapter(tadapter);


        tadapter.setOnItemClickListener(new DashComposerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DashBoardCardType dbtest = getPathSubtypeDash("PIECE_TYPE").get(position);
                String composer = dbtest.title;
                Log.i("how many", Integer.toString(howManyUnratedLeft("PIECE_TYPE", composer)));


                Intent myIntent = new Intent(MainDashBoard.this, PathView.class);
                myIntent.putExtra("Type", "PIECE_TYPE");
                myIntent.putExtra("Subtype", composer);
                MainDashBoard.this.startActivity(myIntent);

            }
        });
        if (arrayType.size() == 0) typeRecycler.setVisibility(View.GONE);

    }

    private void periodDashRecycler() {
        periodRecycler.setHasFixedSize(true);
        periodRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<DashBoardCardType> arrayPeriod = getPathSubtypeDash("PIECE_PERIOD");

        padapter = new DashComposerAdapter(arrayPeriod);
        periodRecycler.setAdapter(padapter);


        padapter.setOnItemClickListener(new DashComposerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                DashBoardCardType dbtest = getPathSubtypeDash("PIECE_PERIOD").get(position);
                String composer = dbtest.title;
                Log.i("how many", composer);

                Log.i("how many", Integer.toString(howManyUnratedLeft("PIECE_PERIOD", composer)));


                Intent myIntent = new Intent(MainDashBoard.this, PathView.class);
                myIntent.putExtra("Type", "PIECE_PERIOD");
                myIntent.putExtra("Subtype", composer);
                MainDashBoard.this.startActivity(myIntent);


            }
        });
        if (arrayPeriod.size() == 0) periodRecycler.setVisibility(View.GONE);
    }

    private void navigation_drawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.navpaths1);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navpaths1:
                break;
            case R.id.navallpieces2:
                Intent myIntent = new Intent(MainDashBoard.this, AllPiecesList.class);
                MainDashBoard.this.startActivity(myIntent);
                finish();
                break;


        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public ArrayList<DashBoardCardType> getPaths(String pathtype) {
        ArrayList<DashBoardCardType> arr = new ArrayList<>();
        String selectQuery = "SELECT Distinct " + pathtype + " FROM PIECES ";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                arr.add(new DashBoardCardType(R.drawable.ludwig, cursor.getString(cursor.getColumnIndex(pathtype)),
                        "test"));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return arr;
    }

    public ArrayList<DashBoardCardType> getPathSubtypeDash(String type) {

        dashBoardCardTypeArrayList = getPaths(type);

        ArrayList<DashBoardCardType> arr = new ArrayList<>();
        String selectQuery = "SELECT PATH_SUBTYPE FROM PATHS WHERE PATH_TYPE= " + "'" + type + "'";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                arr.add(new DashBoardCardType(getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PATH_SUBTYPE)).toLowerCase().replace(".", "").replace(" ", ""), "drawable", getPackageName()), cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PATH_SUBTYPE)),
                        getResources().getString(R.string.left_unrated) + Integer.toString(howManyUnratedLeft(type, cursor.getString(cursor.getColumnIndex(DataBaseHelper.Strings.PATH_SUBTYPE))))));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return arr;
    }

    public int howManyUnratedLeft(String type, String subtype) {


        int count = 0;

        String selectQuery="";
        if (type.equals("PIECE_TYPE")) {selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_TYPE='"+subtype+"'"; }
        else if (type.equals("PIECE_COMPOSER")){selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_COMPOSER='"+subtype+"'";}
        else if (type.equals("PIECE_PERIOD")){selectQuery = "SELECT   * FROM PIECES   LEFT  Join (Select * FROM SCORES where SCORES_TYPE='" + type + "' and SCORES_SUBTYPE='" + subtype + "')a On PIECES.PIECE_ID=a.SCORES_PIECE_ID where SCORES_TYPE is null and PIECE_PERIOD='"+subtype+"'";}



        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                count++;

            } while (cursor.moveToNext());
        } else return 0;


        return count;
    }

}