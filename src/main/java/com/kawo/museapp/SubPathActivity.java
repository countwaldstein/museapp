package com.kawo.museapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.kawo.museapp.dashboard.MainDashBoard;
import com.kawo.museapp.firstpath.ComAdapter;
import com.kawo.museapp.firstpath.ComAdapterSimple;
import com.kawo.museapp.firstpath.ComRecyclerItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class SubPathActivity extends AppCompatActivity {
    private ArrayList<ComRecyclerItem> comRecyclerItemList;
    private RecyclerView mRecyclerView;
    private ComAdapterSimple mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDataBase;
    private ComRecyclerItem mComRecyclerItem;
    private String[] composers = {"PIECE_COMPOSER"};
    private static final String TAG = "SubPathActivity";
    private TextView subPathTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_path);
        mDataBaseHelper = new DataBaseHelper(SubPathActivity.this);
        mDataBase = mDataBaseHelper.getWritableDatabase();
        subPathTitle = findViewById(R.id.textGrid);

        fade();
        Bundle bundle = getIntent().getExtras();
        String pathtype = bundle.getString("Type");
        Log.i("Path type", pathtype);

        if (pathtype.equals("PIECE_COMPOSERS")) subPathTitle.setText(R.string.composers);
        else if (pathtype.equals("PIECE_TYPE"))subPathTitle.setText(R.string.composition_type);
        else if (pathtype.equals("PIECE_PERIOD"))subPathTitle.setText(R.string.period);


            createList(pathtype);
        buildRecyclerView(pathtype);

    }


    public void createList(String pathtype) {
        comRecyclerItemList = new ArrayList<>();
        comRecyclerItemList = getPathSubtypes(pathtype);
    }

    public void buildRecyclerView(final String pathtype) {
        mRecyclerView = findViewById(R.id.comrecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ComAdapterSimple(comRecyclerItemList);


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ComAdapterSimple.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SharedPreferences pref = SubPathActivity.this.getSharedPreferences("shared", 0);
                SharedPreferences.Editor editor = pref.edit();
                boolean firstRun = pref.getBoolean("firstRun", true);

                if (firstRun) {
                    editor.putBoolean("firstRun", false);
                    editor.commit();
                }

                comRecyclerItemList.get(position);
                mComRecyclerItem = comRecyclerItemList.get(position);
                String composer = mComRecyclerItem.getText1();



                addPath(pathtype, composer);
                Intent myIntent = new Intent(SubPathActivity.this, MainDashBoard.class);
                SubPathActivity.this.startActivity(myIntent);
                finish();

            }
        });
    }

    public void fade() {
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);

        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }


    public ArrayList<ComRecyclerItem> getPathSubtypes(String pathtype) {


        ArrayList<ComRecyclerItem> arr = new ArrayList<>();
        String selectQuery = "SELECT Distinct "+pathtype+" FROM PIECES ";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                arr.add(new ComRecyclerItem(getResources().getIdentifier(cursor.getString(cursor.getColumnIndex(pathtype)).toLowerCase(), "drawable", getPackageName()), cursor.getString(cursor.getColumnIndex(pathtype)),
                        ""));


            } while (cursor.moveToNext());
        }

        cursor.close();

        return arr;
    }
    public ArrayList<ComRecyclerItem> getExistingPathSubtypes(String pathtype) {
        Log.i("czy tu dojdzie",   "hmm");
        Log.i("czy tu dojdzie",   pathtype);
        ArrayList<ComRecyclerItem> arr = new ArrayList<>();
        String selectQuery = "SELECT Distinct PATH_SUBTYPE FROM PATHS WHERE PATH_TYPE='"+pathtype+"'";

        Cursor cursor = mDataBase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                {arr.add(new ComRecyclerItem(getResources().getIdentifier(cursor.getString(cursor.getColumnIndex("PATH_SUBTYPE")).toLowerCase(), "drawable", getPackageName()), cursor.getString(cursor.getColumnIndex("PATH_SUBTYPE")),
                        "test"));}


            } while (cursor.moveToNext());
            Log.i("czy tu dojdzie",   "hmm");
        }

        cursor.close();

        return arr;
    }
    void addPath(String type, String subType) {

        Log.i(TAG, type + "type");

        comRecyclerItemList = getExistingPathSubtypes(type);
        boolean isonlist = false;
        Log.i(TAG, subType + "qweqweqweewq");

        for (int i = comRecyclerItemList.size(); i > 0; i--) {
            mComRecyclerItem = comRecyclerItemList.get(i - 1);
            String composerholder = mComRecyclerItem.getText1();
            Log.i(TAG, composerholder + "qrerererererere");
            if (subType.equals(composerholder)) {
                isonlist = true;
                break;

            } else continue;
        }
        if (isonlist) {
            Toast.makeText(SubPathActivity.this, "Path already on the list", Toast.LENGTH_SHORT).show();
        } else {
            mDataBaseHelper.insertPath(type, subType);
//            Toast.makeText(SubPathActivity.this, subType + " successfully added to the list", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(SubPathActivity.this, MainDashBoard.class);
        startActivity(intent);
        finish();
    }
}