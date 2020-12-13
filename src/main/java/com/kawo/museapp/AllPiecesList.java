package com.kawo.museapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kawo.museapp.dashboard.MainDashBoard;
import com.kawo.museapp.firstpath.ComAdapter;
import com.kawo.museapp.firstpath.ComRecyclerItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class AllPiecesList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menuIcon;
    LinearLayout contentView;
    private ComAdapter mAdapter;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDataBase;
    private ComRecyclerItem mComRecyclerItem;
    private ArrayList<ComRecyclerItem> comRecyclerItemList;
    private RecyclerView.LayoutManager mLayoutManager;
    static final float END_SCALE = 0.7f;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pieces_list);
        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.allpieces_navigationview);
        menuIcon = findViewById(R.id.nav_open_button_dash);
        contentView = findViewById(R.id.content_allpieces);
        mDataBaseHelper = new DataBaseHelper(AllPiecesList.this);
        mDataBase = mDataBaseHelper.getWritableDatabase();
        navigation_drawer();
        createList();
        buildRecyclerView();



    }
    public void createList() {
        comRecyclerItemList = new ArrayList<>();
        comRecyclerItemList = getAllPieces();
    }



    private void navigation_drawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener( this);
        navigationView.setCheckedItem(R.id.navallpieces2);
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

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navpaths1:
                Intent myIntent = new Intent(AllPiecesList.this, MainDashBoard.class);
                AllPiecesList.this.startActivity(myIntent);finish();
                break;
            case R.id.navallpieces2:break;


        }
        return true;
    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            {Intent myIntent = new Intent(AllPiecesList.this, MainDashBoard.class);
        AllPiecesList.this.startActivity(myIntent);
        finish();}

    }
    public ArrayList<ComRecyclerItem> getAllPieces() {


        ArrayList<ComRecyclerItem> arr = new ArrayList<>();
        String selectQuery = "SELECT * FROM PIECES ORDER BY PIECE_COMPOSER DESC ";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                arr.add(new ComRecyclerItem(0, cursor.getString(cursor.getColumnIndex("PIECE_NAME")), cursor.getString(cursor.getColumnIndex("PIECE_COMPOSER"))));


            } while (cursor.moveToNext());
        }

        cursor.close();

        return arr;
    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.db_recycler_composers);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ComAdapter(comRecyclerItemList);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ComAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                comRecyclerItemList.get(position);
                mComRecyclerItem = comRecyclerItemList.get(position);



                Intent myIntent = new Intent(AllPiecesList.this, MainDashBoard.class);

                AllPiecesList.this.startActivity(myIntent);
                finish();

            }
        });
    }


}